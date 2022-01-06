package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.vo.QuestionVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

@SpringBootTest
@Slf4j
public class QuestionServiceTest {

    @Autowired
    IQuestionService iQuestionService;

    @Test
    @WithMockUser(username = "11111111111", password = "123456")
    public void getMyQuestions() {
        PageInfo<Question> pageInfo = iQuestionService.getMyQuestions(1, 8);
        System.out.println(pageInfo);
        List<Question> questions = pageInfo.getList();
        questions.forEach(question -> System.out.println(question));
    }

    @Test
    @WithMockUser(username = "11111111111", password = "123456")
    public void saveQuestion() {
        QuestionVO questionVO = new QuestionVO();
        questionVO.setTitle("這是測試問題儲存")
                .setContent("<p>目前正在練習基礎語法，練習到這段：</p><p><br></p><p><br></p><p><br></p><p>public class AutoBoxingDemo{</p><p><br></p><p>&nbsp; &nbsp; public static void main(String[] args){</p><p><br></p><p>&nbsp; &nbsp; &nbsp; &nbsp; Integer i1 = 200;</p><p><br></p><p>&nbsp; &nbsp; &nbsp; &nbsp; Integer i2 = 200;</p><p><br></p><p>&nbsp; &nbsp; &nbsp; &nbsp; System.out.println(\"i1 == i2?\" + (i1 == i2));</p><p><br></p><p>&nbsp; &nbsp;}</p><p><br></p><p>}</p><p><br></p><p><br></p><p><br></p><p>想問一下為何這裡的結果是false呢?</p><p><br></p><p>兩者都是值為200的Integer實例，照理來說應該會連結到同一個實例才對吧?</p>")
                .setTagNames(new String[]{"面试题 ","Spring"})
                .setTeacherNicknames(new String[]{"查理","帥氣約翰"});
        iQuestionService.saveQuestion(questionVO);
    }

    @Test
    public void countQuestionsByUserId(){
        Integer userId=19;
        Integer count=iQuestionService.countQuestionsByUserId(userId);
        System.out.println(count);
    }

    @Test
    public void getQuestionsByTeacherName(){
        PageInfo<Question> pageInfo=iQuestionService.getQuestionsByTeacherName("22222222222",1,8);
        log.debug("pageInfo{}",pageInfo);
        pageInfo.getList().forEach(question -> log.debug("{}",question));
    }

    @Test
    public void getQuestionById(){
        Integer questionId=48;
        Question question=iQuestionService.getQuestionsById(questionId);
        log.debug("question{}",question);
    }
}
