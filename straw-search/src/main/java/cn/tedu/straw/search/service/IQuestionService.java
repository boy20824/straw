package cn.tedu.straw.search.service;

import cn.tedu.straw.search.vo.QuestionVO;
import com.github.pagehelper.PageInfo;

public interface IQuestionService {
    /*
    同步方法,將straw-faq服務模塊中的問題訊息讀取出來
    然後保存到當前straw-search的ES數據庫中
     */
    void sync();

    /**
     * 根據key搜索Elasticsearch中的問題數據,返回當前用戶的問題或者已經公開的問題
     * @param key 搜尋關鍵字
     * @param username 當前用戶的名子
     * @param pageNum 當前頁號
     * @param pageSize 頁面大小
     * @return 一頁搜索結果
     */
    PageInfo<QuestionVO> search(String key,String username,Integer pageNum,Integer pageSize);
}
