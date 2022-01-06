package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.mapper.CommentMapper;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.ICommentService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.util.Keys;
import cn.tedu.straw.portal.vo.CommentVO;
import cn.tedu.straw.portal.vo.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    UserMapper userMapper;

    @Resource
    CommentMapper commentMapper;

    @Override
    @Transactional
    public Comment saveComment(CommentVO commentVO, String username) {
        User user = userMapper.findUserByUsername(username);
        Comment comment = new Comment()
                .setUserNickName(user.getNickName())
                .setUserId(user.getId())
                .setContent(commentVO.getContent())
                .setCreateTime(LocalDateTime.now())
                .setAnswerId(commentVO.getAnswerId());
        int rows = commentMapper.insert(comment);

        if (rows != 1) {
            throw ServiceException.busy();
        }
        return comment;
    }

    @Override
    public boolean removeComment(Integer commentId, String username) {
        User user = userMapper.findUserByUsername(username);
        //AccountType的值是1,表示是老師
        if (Keys.MASTER.equals(user.getAccountType())) {
            int rows = commentMapper.deleteById(commentId);
            return rows == 1;
        }
        Comment comment = commentMapper.selectById(commentId);
        if (comment.getUserId().equals(user.getId())) {
            int rows = commentMapper.deleteById(commentId);
            return rows == 1;
        }
        throw new ServiceException("權限不足");
    }

    @Override
    @Transactional
    public Comment updateComment(CommentVO commentVO, Integer commentId, String username) {
        User user = userMapper.findUserByUsername(username);
        Comment comment = commentMapper.selectById(commentId);
        if (Keys.MASTER.equals(user.getAccountType()) ||
                comment.getUserId().equals(user.getId())) {
            comment.setContent(commentVO.getContent());
            int rows = commentMapper.updateById(comment);
            if (rows != 1) {
                throw ServiceException.busy();
            }
            return comment;
        }
        throw new ServiceException("權限不足!");
    }
}
