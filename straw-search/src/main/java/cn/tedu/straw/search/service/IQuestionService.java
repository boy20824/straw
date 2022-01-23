package cn.tedu.straw.search.service;

public interface IQuestionService {
    /*
    同步方法,將straw-faq服務模塊中的問題訊息讀取出來
    然後保存到當前straw-search的ES數據庫中
     */
    void sync();
}
