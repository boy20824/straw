package cn.tedu.straw.faq;

import cn.tedu.straw.commons.model.Answer;
import cn.tedu.straw.commons.model.Question;
import cn.tedu.straw.faq.service.IAnswerService;
import cn.tedu.straw.faq.service.IQuestionService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
public class ServiceTest {

    @Resource
    private IQuestionService iQuestionService;

    @Resource
    private IAnswerService iAnswerService;

    @Test
    public void service() {

        PageInfo<Question> pageInfo = iQuestionService.getQuestionsByTeacherName("22222222222", 1, 10);
        pageInfo.getList().forEach(question -> log.debug("{}", question));

    }

    @Test
    public void getAnswerByQuestionId() {
        List<Answer> answerList = iAnswerService.getAnswersByQuestionId(46);
        answerList.forEach(answer -> log.debug("回覆答案{}", answer));
    }

    @Test
    void getQuestions(){
        PageInfo<Question> pageInfo=iQuestionService.getQuestions(1,10);
        pageInfo.getList().forEach(question -> log.debug("{}",question));
        log.debug("{}",pageInfo);
    }
}
