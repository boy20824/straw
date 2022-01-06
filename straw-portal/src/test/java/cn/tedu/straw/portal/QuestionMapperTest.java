package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
public class QuestionMapperTest {

    @Resource
    QuestionMapper questionMapper;

    @Test
    public void findTeachersQuestionsById(){
        Integer userId=1;
        List<Question> questions=questionMapper.findTeachersQuestions(userId);
        questions.forEach(question -> log.debug("{}",question));
    }

    @Test
    public void updateStatus(){
        int rows=questionMapper.updateStatus(51,Question.SOLVED);
        log.debug("{}",rows);
    }
}
