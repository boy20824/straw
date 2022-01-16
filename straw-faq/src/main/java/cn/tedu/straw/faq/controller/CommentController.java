package cn.tedu.straw.faq.controller;


import cn.tedu.straw.commons.model.Comment;
import cn.tedu.straw.faq.service.ICommentService;
import cn.tedu.straw.faq.vo.CommentVO;
import cn.tedu.straw.commons.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@RestController
@RequestMapping("/v1/comments")
@Slf4j
public class CommentController {

    @Resource
    ICommentService commentService;

    @PostMapping("")
    public R<Comment> postComment(@Validated CommentVO commentVO, BindingResult result,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return R.invalidRequest(message);
        }
        log.debug("{}", commentVO);

        String username = userDetails.getUsername();
        Comment comment = commentService.saveComment(commentVO, username);
        return R.created(comment);
    }

    /**
     * 請求url /v1/comments/157/delete
     */
    @GetMapping("/{commentId}/delete")
    public R removeComment(@PathVariable Integer commentId, @AuthenticationPrincipal User user) {
        log.debug("CommentId={}", commentId);

        boolean del = commentService.removeComment(commentId, user.getUsername());

        if (del) {
            return R.gone("刪除成功");
        } else {
            return R.notFound("刪除失敗");
        }

    }


    /**
     * 更新評論內容
     *
     * @param commentId
     * @param commentVO
     * @param result
     * @param user
     * @return
     */
    @PostMapping("/{commentId}/update")
    public R<Comment> updateComments(@PathVariable Integer commentId,
                                     @Validated CommentVO commentVO, BindingResult result,
                                     @AuthenticationPrincipal User user) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return R.unprocessableEntity(message);
        }
        log.debug("表單訊息CommentVo {}", commentVO);
        Comment comment = commentService.updateComment(commentVO, commentId, user.getUsername());
        return R.ok(comment);
    }

}
