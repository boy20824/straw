package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.model.Answer;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
public class AnswerMapperTest {

    @Resource
    AnswerMapper answerMapper;

    @Test
    public void findAnswersByQuestionId(){
        List<Answer> answers=answerMapper.findAnswersByQuestionId(46);
        answers.forEach(answer -> log.debug("{}",answer));
    }

    @Test
    public  void acceptedStatus(){
        int rows=answerMapper.updateAcceptedStatus(12,1);
        log.debug("{}",rows);
    }
}
