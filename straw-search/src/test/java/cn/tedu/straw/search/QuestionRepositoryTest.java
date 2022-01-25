package cn.tedu.straw.search;

import cn.tedu.straw.search.mapper.QuestionRepository;
import cn.tedu.straw.search.vo.QuestionVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class QuestionRepositoryTest {

    @Resource
    QuestionRepository questionRepository;

    @Test
    public void queryAllByParams(){
        Pageable pageable= PageRequest.of(0,3);
        Page<QuestionVO> page=questionRepository.queryAllByParam(
                "java","java",1,pageable);

        page.getContent().forEach(questionVO -> log.debug("{}",questionVO));
    }
}
