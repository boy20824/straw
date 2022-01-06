package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.vo.QuestionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
public interface IQuestionService extends IService<Question> {

    /**
     * 顯示當前學生自己的全部問題
     * @param pageNum 當前頁號
     * @param pageSize 翻頁時候的頁面大小
     * @return 當前用戶的全部問題
     */
    PageInfo<Question> getMyQuestions(Integer pageNum,Integer pageSize);

    /**
     * 將用戶提交的問題保存到數據庫中
     * @param questionVO 用戶提交到問題表單數據
     */
    void saveQuestion(QuestionVO questionVO);

    /**
     * 依照用戶id查詢用戶提交的問題數量
     * 刪除掉的不會統計 (刪除標記)
     * @param userId 用戶id
     * @return 問題數量
     */
    Integer countQuestionsByUserId(Integer userId);


    /**
     * 查詢老師相關問題
     * @return
     */
    PageInfo<Question> getQuestionsByTeacherName(String username,Integer pageNum,Integer pageSize);

    /**
     * 根據問題的ID查詢一個問題數據
     */
    Question getQuestionsById(Integer id);
}
