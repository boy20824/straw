package cn.tedu.straw.sys.service;

import cn.tedu.straw.commons.model.Permission;
import cn.tedu.straw.commons.model.Role;
import cn.tedu.straw.commons.model.User;
import cn.tedu.straw.sys.vo.RegisterVo;
import cn.tedu.straw.sys.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;


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

    void registerStudent(RegisterVo registerVo);

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
    UserVO getCurrentUserVo(String username);

    User getUserByUsername(String username);

    List<Permission> getUserPermissions(Integer userId);

    List<Role> getUserRoles(Integer userId);
}
