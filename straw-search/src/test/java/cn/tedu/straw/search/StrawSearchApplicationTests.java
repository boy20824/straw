package cn.tedu.straw.search;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
class StrawSearchApplicationTests {

    @Resource //Operations操作
    ElasticsearchOperations elasticsearchOperations;

    @Test
    void contextLoads() {
        log.debug("{}",elasticsearchOperations);
    }

}
