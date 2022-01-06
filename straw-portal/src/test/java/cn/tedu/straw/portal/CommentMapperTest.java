package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.CommentMapper;
import cn.tedu.straw.portal.model.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class CommentMapperTest {

    @Resource
    CommentMapper commentMapper;

    @Test
    public void selectById(){
        Comment comment=commentMapper.selectById(4);
        log.debug("Comment{}",comment);
    }
}
