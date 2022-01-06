package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.AnswerVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@Service
@Slf4j
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

    @Resource
    private AnswerMapper answerMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public Answer saveAnswer(AnswerVO answerVO, String username) {
        User user = userMapper.findUserByUsername(username);
        Answer answer = new Answer()
                .setUserNickName(user.getNickName())
                .setUserId(user.getId())
                .setQuestionId(answerVO.getQuestionId())
                .setContent(answerVO.getContent())
                .setLikeCount(0)
                .setCreateTime(LocalDateTime.now())
                .setAcceptedStatus(0);
        int rows = answerMapper.insert(answer);
        if (rows != 1) {
            throw new ServiceException("數據庫忙碌中,請稍後再嘗試");
        }
        return answer;
    }

    @Override
    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        if (questionId == null) {
            throw ServiceException.notFound("問題ID不得為空");
        }
        List<Answer> answers = answerMapper.findAnswersByQuestionId(questionId);
        return answers;
    }

    @Override
    @Transactional
    public boolean accept(Integer answerId) {
        //查詢當前的答案
        Answer answer=answerMapper.selectById(answerId);
        if(answer==null){
            throw ServiceException.notFound("沒有找到相關數據");
        }
        //如果被接受過,則不處理
        if(answer.getAcceptedStatus().equals(1)){
            return false;
        }

        int rows=answerMapper.updateAcceptedStatus(answerId,1);

        if(rows!=1){
            throw ServiceException.busy();
        }

        rows =questionMapper.updateStatus(answer.getQuestionId(), Question.SOLVED);

        if(rows!=1){
            throw ServiceException.busy();
        }

        log.debug("將問題和答案更新為以解決的狀態:{}",answerId);
        return true;
    }
}
