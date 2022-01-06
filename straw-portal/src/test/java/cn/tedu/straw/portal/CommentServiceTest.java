package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.service.ICommentService;
import cn.tedu.straw.portal.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class CommentServiceTest {

    @Resource
    ICommentService iCommentService;

    @Test
    public void saveComment() {
        CommentVO commentVO = new CommentVO()
                .setContent("這是一個內部測試")
                .setAnswerId(6);
        String username = "22222222222";
        Comment comment= iCommentService.saveComment(commentVO, username);
        log.debug("{}",comment);
    }

    @Test
    public void removeComment(){
        int commentId=13;
        String username = "22222222222";
        boolean b= iCommentService.removeComment(commentId,username);
        log.debug("del {}",b);

    }
}
