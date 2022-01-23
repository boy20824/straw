package cn.tedu.straw.search;

import cn.tedu.straw.search.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class QuestionServiceTest {

    @Resource
    IQuestionService iQuestionService;

    @Test
    public void sync(){
        iQuestionService.sync();
    }
}
