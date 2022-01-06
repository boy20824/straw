package cn.tedu.straw.commons.service;

import cn.tedu.straw.commons.vo.R;

/*
業務層異常
包含異常號code
 */
public class ServiceException extends RuntimeException {
    private int code = R.INTERNAL_SERVER_ERROR;

    public ServiceException() {
    }

    public int getCode() {
        return code;
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public static ServiceException invalidRequest(String message){
        return new ServiceException(message,R.INVALID_REQUEST);
    }

    /*
    用戶發出的請求針對的是不存在的紀錄,服務器沒有進行操作
    get獲取對象,但是沒有這個對象紀錄
     */
    public static ServiceException notFound(String message) {
        return new ServiceException(message, R.NOT_FOUND);
    }

    /*
    用戶請求的資源被永久刪除,且不會再得到的
     */
    public static ServiceException gone(String message) {
        return new ServiceException(message, R.GONE);
    }

    //收到表單數據,無法進行處理的時候,拋出"無法處理異常實體"異常
    public static ServiceException unprocessableEntity(String message) {
        return new ServiceException(message, R.UNPROCESSABLE_ENTITY);
    }

    public static ServiceException busy(){
        return  new ServiceException("數據庫繁忙",R.INTERNAL_SERVER_ERROR);
    }
}
