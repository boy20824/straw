package cn.tedu.straw.search.service.impl;

import cn.tedu.straw.search.mapper.QuestionRepository;
import cn.tedu.straw.search.service.IQuestionService;
import cn.tedu.straw.search.vo.QuestionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;

@Service
@Slf4j
public class QuestionServiceImpl implements IQuestionService {

    @Resource
    private QuestionRepository questionRepository;

    @Resource
    RestTemplate restTemplate;

    @Override
    public void sync() {
        //先獲取總頁數
        String url="http://faq-service/v1/questions/page/count?pageSize={1}";
        int pageSize=10;
        Integer pages=restTemplate.getForObject(url,Integer.class,pageSize);
        for(int i=1;i<pages;i++){
            //每次讀取一頁數據
            url="http://faq-service/v1/questions/page?pageNum={1}&pageSize={2}";
            QuestionVO[] questions=restTemplate.getForObject(url,QuestionVO[].class,i,pageSize);
            //將讀取到一頁數據儲存到ES中
            questionRepository.saveAll(Arrays.asList(questions));
            log.debug("保存page {}",i);
        }

    }
}
