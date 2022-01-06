package cn.tedu.straw.portal.vo;

import cn.tedu.straw.portal.service.ServiceException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class R<T> implements Serializable {

    // 200 ok[Get]: 服務器成功返回請求的數據
    public static final int OK = 200;
    // 201 create[post/put/patch]: 用戶新建或修改數據成功
    public static final int CREATED = 201;
    // 202 accepted :表示一個請求已經進入後台排隊(異步任務)
    public static final int ACCEPTED = 202;
    // 204 not content[delete]:用戶刪除數據成功
    public static final int NO_CONTENT = 204;
    // 400 invalid request[post/put/patch]:用戶發出的請求有錯誤,服務器沒有進行新建或修改數據的操作
    public static final int INVALID_REQUEST = 400;
    // 401 unauthorized :用戶沒有權限(令牌,用戶名,密碼錯誤)
    public static final int UNAUTHORIZED = 401;
    // 403 forbidden : 用戶得到授權(與401錯誤相對),但是訪問是被禁止的
    public static final int FORBIDDEN = 403;
    // 404 not found : 用戶發出的請求針對的是不存在的紀錄,服務器沒有進行操作
    public static final int NOT_FOUND = 404;
    // 410 Gone[get] :用戶請求的資源被永久刪除,且不會再得到的
    public static final int GONE = 410;
    // 422 unprocessable entity[post/put/patch]:當創建一個對象時,發生一個驗證錯誤
    public static final int UNPROCESSABLE_ENTITY = 422;
    // 500 internal server error :服務器發生錯誤,用戶無法判斷發出的請求是否成功
    public static final int INTERNAL_SERVER_ERROR = 500;

    private int code;
    private String message;
    private T data;

    /*
    服務器成功返回用戶請求的數據
    消息
     */
    public static R ok(String message) {
        return new R().setCode(OK).setMessage(message);
    }

    /*
    服務器成功返回用戶請求的數據
    數據
     */
    public static R ok(Object data) {
        return new R().setMessage("OK").setCode(OK).setData(data);
    }

    /*
    用戶新建或修改數據成功
     */
    public static R created(String message) {
        return new R().setCode(CREATED).setMessage(message);
    }

    public static R created(Object data) {
        return new R().setCode(CREATED).setData(data).setMessage("創建成功");
    }

    /*
    表示一個請求已經進入後台排隊(異步任務)
     */
    public static R accepted(String message) {
        return new R().setCode(ACCEPTED).setMessage(message);
    }

    /*
    用戶刪除數據成功
     */
    public static R noContent(String message) {
        return new R().setCode(NO_CONTENT).setMessage(message);
    }

    /*
    用戶發出請求有錯誤,服務器沒有進行新建或修改數據的操作
     */
    public static R invalidRequest(String message) {
        return new R().setCode(INVALID_REQUEST).setMessage(message);
    }

    /*
    表示用戶沒有權限(令牌,用戶名,密碼錯誤)
     */
    public static R unauthorized(String message) {
        return new R().setCode(UNAUTHORIZED).setMessage(message);
    }

    /*
    登陸以後,但是沒有足夠權限
     */
    public static R forbidden(String message) {
        return new R().setCode(FORBIDDEN).setMessage(message);
    }

    /*
    用戶發出的請求針對的是不存在的紀錄,服務器沒有進行操作
     */
    public static R notFound(String message) {
        return new R().setCode(NOT_FOUND).setMessage(message);
    }

    /*
    用戶請求的資源被永久刪除,且不會再得到的
     */
    public static R gone(String message) {
        return new R().setCode(GONE).setMessage(message);
    }

    /*
    當創建一個對象時,發生一個驗證錯誤
     */
    public static R unprocessableEntity(String message) {
        return new R().setCode(UNPROCESSABLE_ENTITY).setMessage(message);
    }

    /*
    將異常消息複製到返回結果中
     */
    public static R failed(ServiceException e) {
        return new R().setCode(e.getCode()).setMessage(e.getMessage());
    }


    /*
    服務器發生錯誤,用戶無法判斷發出的請求是否成功
    */
    public static R failed(Throwable e) {
        return new R().setCode(INTERNAL_SERVER_ERROR).setMessage(e.getMessage());
    }

}
