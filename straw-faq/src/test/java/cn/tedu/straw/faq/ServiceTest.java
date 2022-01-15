package cn.tedu.straw.faq;

import cn.tedu.straw.commons.model.Question;
import cn.tedu.straw.faq.service.IQuestionService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class ServiceTest {

    @Resource
    private IQuestionService iQuestionService;

    @Test
    public void service(){

        PageInfo<Question> pageInfo=iQuestionService.getQuestionsByTeacherName("22222222222",1,10);
        pageInfo.getList().forEach(question -> log.debug("{}",question));

    }
}
