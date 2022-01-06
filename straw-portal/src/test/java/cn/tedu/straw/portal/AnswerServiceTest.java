package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.vo.AnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
public class AnswerServiceTest {

    @Resource
    IAnswerService iAnswerService;

    @Test
    public void saveAnswer() {
        String username = "22222222222";
        AnswerVO answerVO = new AnswerVO()
                .setQuestionId(47)
                .setContent("測試性問題答案!");
        Answer answer = iAnswerService.saveAnswer(answerVO, username);
        log.debug("Answer{}", answer);
    }

    @Test
    public void getAnswersByQuestionId() {
        List<Answer> answerList = iAnswerService.getAnswersByQuestionId(46);
        answerList.forEach(answer -> log.debug("回覆答案{}",answer));
    }

    @Test
    public void accept()  {
        int answerId=13;
        boolean b=iAnswerService.accept(answerId);
        System.out.println(b);
    }
}
