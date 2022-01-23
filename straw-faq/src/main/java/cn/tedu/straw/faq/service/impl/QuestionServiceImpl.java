package cn.tedu.straw.faq.service.impl;

import cn.tedu.straw.commons.model.*;
import cn.tedu.straw.faq.mapper.QuestionMapper;
import cn.tedu.straw.faq.mapper.QuestionTagMapper;
import cn.tedu.straw.faq.mapper.UserQuestionMapper;
import cn.tedu.straw.faq.service.IQuestionService;
import cn.tedu.straw.faq.service.ITagService;
import cn.tedu.straw.commons.service.ServiceException;
import cn.tedu.straw.faq.vo.QuestionVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    ITagService iTagService;

    @Autowired
    QuestionTagMapper questionTagMapper;

    @Autowired
    UserQuestionMapper userQuestionMapper;

    @Resource
    RibbonClient ribbonClient;

    @Override
    public PageInfo<Question> getMyQuestions(String username, Integer pageNum, Integer pageSize) {

        if (pageNum == null || pageSize == null) {
            throw new ServiceException("翻頁參數錯誤");
        }

//        String username = iUserService.currentUsername();
        log.debug("當前用戶是{}", username);
        //http://sys-service/v1/auth/user?username={1}
//        String url = "http://sys/v1/auth/user?username={1}";
//        User user = restTemplate.getForObject(url, User.class, username);
        User user = ribbonClient.getUser(username);
//        User user = userMapper.findUserByUsername(username);
        log.debug("當前登錄用戶{}", user);
        if (user == null) {
            throw ServiceException.notFound("找不到當前用戶!");
        }
        QueryWrapper<Question> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        queryWrapper.eq("delete_status", 0);
        queryWrapper.orderByDesc("create_time");

        PageHelper.startPage(pageNum, pageSize);
        List<Question> list = questionMapper.selectList(queryWrapper);
        log.debug("查詢得到{}行數據", list.size());
        for (Question q : list) {
            List<Tag> tags = tagNamesToTags(q.getTagNames());
            q.setTags(tags);
        }
        return new PageInfo<>(list);

    }

    /**
     * 將標籤名列表轉換為標籤列表集合
     *
     * @param tagNames 標籤名列表
     * @return 標籤列表集合
     */
    private List<Tag> tagNamesToTags(String tagNames) {
        String[] names = tagNames.split(",\\s?");
        Map<String, Tag> name2TagMap = iTagService.getName2TagMap();
        List<Tag> tags = new ArrayList<>();
        for (String name : names) {
            Tag tag = name2TagMap.get(name);
            tags.add(tag);
        }
        return tags;
    }

    @Override
    @Transactional // 聲明式事務,當前方法中的SQL作為整體執行,執行失敗會回滾到開始階段
    public void saveQuestion(String username, QuestionVO questionVO) {
        //儲存問題
        log.debug("問題訊息{}", questionVO);
        //獲取當前用戶
//        String username = iUserService.currentUsername();
        //獲取用戶全部訊息
//        String url = "http://sys/v1/auth/user?username={1}";
//        User user = userMapper.findUserByUsername(username);
        User user = ribbonClient.getUser(username);
        log.debug("當前用戶訊息{}", user);
        //根據標籤名數組創建標籤名列表
        StringBuilder builder = new StringBuilder();
        for (String tagName : questionVO.getTagNames()) {
            builder.append(tagName).append(",");
        }

        String tagNames = builder.deleteCharAt(builder.length() - 1).toString();
        log.debug("標籤名列表{}", tagNames);
        Question question = new Question()
                .setTitle(questionVO.getTitle())
                .setContent(questionVO.getContent())
                .setStatus(0) //0 表示剛剛創建的問題還沒有老師回覆
                .setPublicStatus(0) // 0 表示只能學員自己查看問題
                .setDeleteStatus(0) // 0 表示沒有刪除, 1 表示已經刪除
                .setHits(0) // 問題被查看到的次數
                .setCreateTime(LocalDateTime.now())
                .setModifyTime(LocalDateTime.now())
                .setUserNickName(user.getNickName())
                .setUserId(user.getId())
                .setTagNames(tagNames) //問題相關的標籤名列表
                ;
        log.debug("問題訊息{}", question);
        int row = questionMapper.insert(question);
        if (row != 1) {
            throw new ServiceException("數據庫繁忙中,請稍後在嘗試");
        }
        log.debug("問題訊息{}", question);

        //保存問題和標籤的關西 questionVO.getTagNames()
        //根據標籤名找到標籤ID,問題ID和標籤ID儲存到questionTag表
        Map<String, Tag> name2TagMap = iTagService.getName2TagMap();
        for (String tagName : questionVO.getTagNames()) {
            //根據標籤名,查找到其對應到標籤信息
            Tag tag = name2TagMap.get(tagName);
            if (tag == null) {
                throw ServiceException.unprocessableEntity("標籤名錯誤!!");
            }
            QuestionTag questionTag = new QuestionTag();
            questionTag.setQuestionId(question.getId())
                    .setTagId(tag.getId())
                    .setCreateTime(LocalDateTime.now())
                    .setModifyTime(LocalDateTime.now())
            ;
            //將標籤和問題對應關西儲存到數據庫
            row = questionTagMapper.insert(questionTag);
            if (row != 1) {
                throw new ServiceException("數據庫繁忙中,請稍後在嘗試");
            }
        }


        //保存問題和答題老師關西 questionVo.getTeacherNicknames()
//        Map<String, User> masterMap = iUserService.getMasterMap();
        //利用Ribbon從Straw-sys服務中全部回答問題的老師
//        String url = "http://sys-service/v1/users/masters";
//        User[] users = restTemplate.getForObject(url, User[].class);
        User[] users = ribbonClient.masters();
        Map<String, User> masterMap = new HashMap<>();
        for (User u : users) {
            masterMap.put(u.getNickName(), u);
        }
        for (String nickname : questionVO.getTeacherNicknames()) {
            User master = masterMap.get(nickname);
            if (master == null) {
                throw ServiceException.unprocessableEntity("老師名稱錯誤");
            }
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setQuestionId(question.getId())
                    .setUserId(master.getId())
                    .setCreateTime(LocalDateTime.now())
                    .setModifyTime(LocalDateTime.now());
            row = userQuestionMapper.insert(userQuestion);
            if (row != 1) {
                throw new ServiceException("數據庫繁忙中,請稍後在嘗試");
            }
        }
    }

    @Override
    public Integer countQuestionsByUserId(Integer userId) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("delete_status", 0); //未刪除的
        //selectCount 是MybatisPlus提供的統計查詢方法,專門用於統計數量
        Integer count = questionMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public PageInfo<Question> getQuestionsByTeacherName(String username, Integer pageNum, Integer pageSize) {
        if (username == null) {
            throw ServiceException.notFound("用戶名不能為空");
        }

        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageNum = 8;
        }

//        User user = userMapper.findUserByUsername(username);
        User user = ribbonClient.getUser(username);

        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.findTeachersQuestions(user.getId());

        //填充標籤列表屬性 tags
        for (Question question : questions) {
            List<Tag> tags = tagNamesToTags(question.getTagNames());
            question.setTags(tags);
        }
        return new PageInfo<>(questions);
    }

    @Override
    public Question getQuestionsById(Integer id) {
        Question question = questionMapper.selectById(id);
        //填充tags屬性
        List<Tag> tags = tagNamesToTags(question.getTagNames());
        question.setTags(tags);
        return question;
    }

    @Override
    public PageInfo<Question> getQuestions(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //為null時,查詢所有數據
        List<Question> list=questionMapper.selectList(null);
        return new PageInfo<>(list);
    }
}
