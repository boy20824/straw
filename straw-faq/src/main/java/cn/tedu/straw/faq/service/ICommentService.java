package cn.tedu.straw.faq.service;

import cn.tedu.straw.commons.model.Comment;
import cn.tedu.straw.faq.vo.CommentVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 將用戶發起的評論訊息保存起來
     *
     * @param commentVO 用戶發起的評論表單內容
     * @param username  發起評論的用戶ID
     * @return 創見好的評論
     */
    Comment saveComment(CommentVO commentVO, String username);

    /**
     * 依照評論ID,及使用者判斷能否刪除該評論
     * 老師能刪除任何評論,學生只能刪除自己的
     *
     * @param commentId 評論ID
     * @param username  當前使用者username
     * @return true or false
     */
    public boolean removeComment(Integer commentId, String username);

    /**
     * 更新評論內容
     *
     * @param commentVO 評論內容
     * @param commentId 評論ID
     * @param username  發起修改評論的用戶
     * @return 修改好的評論
     */
    public Comment updateComment(CommentVO commentVO, Integer commentId, String username);

}
