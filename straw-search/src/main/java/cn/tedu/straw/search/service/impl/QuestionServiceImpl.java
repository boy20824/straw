package cn.tedu.straw.search.service.impl;

import cn.tedu.straw.commons.model.Tag;
import cn.tedu.straw.commons.model.User;
import cn.tedu.straw.search.mapper.QuestionRepository;
import cn.tedu.straw.search.service.IQuestionService;
import cn.tedu.straw.search.util.Pages;
import cn.tedu.straw.search.vo.QuestionVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class QuestionServiceImpl implements IQuestionService {

    @Resource
    private QuestionRepository questionRepository;

    @Resource
    RestTemplate restTemplate;

    //緩存標籤數據
    private final ConcurrentHashMap<String, Tag> tagMap = new ConcurrentHashMap<>();

    //第一次在一小時後啟動,之後每一小時清空一次
    @Scheduled(initialDelay = 1000*60*60*24,fixedRate =1000*60*60*24 )
    private void clearTagMap(){
        tagMap.clear();
    }

    private Map<String, Tag> getTagMap() {
        if (tagMap.isEmpty()) {
            synchronized (tagMap) {
                if (tagMap.isEmpty()) {
                    String url = "http://faq-service/v1/tags/list";
                    Tag[] tags = restTemplate.getForObject(url, Tag[].class);
                    for (Tag tag : tags) {
                        tagMap.put(tag.getName(), tag);
                    }
                }
            }
        }
        return tagMap;
    }

    @Override
    public void sync() {
        //先獲取總頁數
        String url = "http://faq-service/v1/questions/page/count?pageSize={1}";
        int pageSize = 10;
        Integer pages = restTemplate.getForObject(url, Integer.class, pageSize);
        for (int i = 1; i < pages; i++) {
            //每次讀取一頁數據
            url = "http://faq-service/v1/questions/page?pageNum={1}&pageSize={2}";
            QuestionVO[] questions = restTemplate.getForObject(url, QuestionVO[].class, i, pageSize);
            //將讀取到一頁數據儲存到ES中
            questionRepository.saveAll(Arrays.asList(questions));
            log.debug("保存page {}", i);
        }
    }

    private User getUser(String username) {
        String url = "http://sys-service/v1/auth/user?username={1}";
        User user = restTemplate.getForObject(url, User.class, username);
        return user;
    }

    @Override
    public PageInfo<QuestionVO> search(
            String key, String username, Integer pageNum, Integer pageSize) {
        User user = getUser(username);

        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 8;
        }
        int page = pageNum - 1;
        int size = pageSize;
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createTime");

        Page<QuestionVO> questions = questionRepository.queryAllByParam(key, key, user.getId(), pageable);
        for(QuestionVO questionVO:questions.getContent()){
            List<Tag> tags=tagNameToTags(questionVO.getTagNames());
            questionVO.setTags(tags);
        }
        return Pages.pageInfo(questions);
    }
    private List<Tag> tagNameToTags(String tagName){
        Map<String,Tag>tagMap=getTagMap();
        String[] names=tagName.split(",\\s?");
        List<Tag> tags=new ArrayList<>();
        for (String name:names){
            Tag tag=tagMap.get(name);
            tags.add(tag);
        }
        System.out.println(tags);
        return tags;
    }

    @Override
    public void saveQuestion(QuestionVO questionVO) {
        questionRepository.save(questionVO);
    }
}
