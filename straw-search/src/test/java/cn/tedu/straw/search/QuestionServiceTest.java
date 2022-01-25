package cn.tedu.straw.search;

import cn.tedu.straw.search.service.IQuestionService;
import cn.tedu.straw.search.vo.QuestionVO;
import com.github.pagehelper.PageInfo;
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

    @Test
    public void search(){
        String username="22222222222";
        String key="JAVA";
        PageInfo<QuestionVO> pageInfo=iQuestionService.search(key,username,1,8);
        pageInfo.getList().forEach(questionVO -> log.debug("questionVO :{}",questionVO));
        log.debug("{}",pageInfo);

    }
}
