package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.vo.RegisterVo;
import cn.tedu.straw.portal.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
public interface IUserService extends IService<User> {

    /*
    根據用戶名獲取用戶詳細訊息
    用於spring-security的登錄操作,登錄時候spring-security會將登錄用戶名
    傳遞到getUserDetails方法,此方法就會去數據庫查找用戶訊息
    由spring決定是否能登錄
     */
    UserDetails getUserDetails(String username);

    void registerStudent(RegisterVo registerVo);
    /*
    返回當前已經登錄的用戶名
    @return 返回username
     */
    String currentUsername();

    /**
     * 返回系統中全部解答問題的老師
     * @return 全部解答問題老師的列表
     */
    List<User> getMasters();

    /**
     * 返回緩存的全部老師暱稱和老師訊息
     * 用於快速的根據老師名稱找到老師訊息
     * @return 全部老師暱稱和老師訊息的Map
     */
    Map<String,User> getMasterMap();

    /**
     * 獲取當前用戶訊息
     * 用於在頁面上顯示當前訊息,訊息包括
     * 用戶發布問題數量,用戶收藏問題的數量
     * @return 用戶訊息
     */
    UserVO getCurrentUserVo();
}
