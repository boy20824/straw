package cn.tedu.straw.faq.service;

import cn.tedu.straw.commons.model.Question;
import cn.tedu.straw.faq.vo.QuestionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
public interface IQuestionService extends IService<Question> {

    /**
     * 顯示當前學生自己的全部問題
     *
     * @param pageNum  當前頁號
     * @param pageSize 翻頁時候的頁面大小
     * @return 當前用戶的全部問題
     */
    PageInfo<Question> getMyQuestions(String username, Integer pageNum, Integer pageSize);

    /**
     * 將用戶提交的問題保存到數據庫中
     *
     * @param questionVO 用戶提交到問題表單數據
     */
    void saveQuestion(String username,QuestionVO questionVO);

    /**
     * 依照用戶id查詢用戶提交的問題數量
     * 刪除掉的不會統計 (刪除標記)
     *
     * @param userId 用戶id
     * @return 問題數量
     */
    Integer countQuestionsByUserId(Integer userId);


    /**
     * 查詢老師相關問題
     *
     * @return
     */
    PageInfo<Question> getQuestionsByTeacherName(String username, Integer pageNum, Integer pageSize);

    /**
     * 根據問題的ID查詢一個問題數據
     */
    Question getQuestionsById(Integer id);

    /**
     * 返回一頁問題數據
     * @param pageNum 頁號從1開始
     * @param pageSize 頁面行數
     * @return 一頁數據
     */
    PageInfo<Question> getQuestions(Integer pageNum,Integer pageSize);
}
