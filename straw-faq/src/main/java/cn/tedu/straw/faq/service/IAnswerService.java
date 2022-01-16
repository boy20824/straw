package cn.tedu.straw.faq.service;

import cn.tedu.straw.commons.model.Answer;
import cn.tedu.straw.faq.vo.AnswerVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
public interface IAnswerService extends IService<Answer> {

    Answer saveAnswer(AnswerVO answerVO,String username);

    /**
     * 查詢一個問題的全部回覆Answer
     * @param questionId 問題編號
     * @return 該問題所有答覆
     */
    List<Answer> getAnswersByQuestionId(Integer questionId);

    /**
     * 接受答案,將答案狀態更新,將問題狀態更新
     * @param answerId 答案ID
     * @return 成功為truec,失敗false
     */
    boolean accept(Integer answerId);
}
