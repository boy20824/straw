package cn.tedu.straw.faq;

import cn.tedu.straw.faq.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class MapperTest {

    @Resource
    QuestionMapper questionMapper;

    @Resource
    QuestionTagMapper questionTagMapper;

    @Resource
    TagMapper tagMapper;

    @Resource
    UserQuestionMapper userQuestionMapper;

    @Resource
    AnswerMapper answerMapper;

    @Resource
    CommentMapper commentMapper;

    @Test
    public void mappers(){
        log.debug("{}",questionMapper);
        log.debug("{}",questionTagMapper);
        log.debug("{}",tagMapper);
        log.debug("{}",userQuestionMapper);
        log.debug("{}",answerMapper);
        log.debug("{}",commentMapper);
    }
}
