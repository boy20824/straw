package cn.tedu.straw.sys.mapper;

import cn.tedu.straw.commons.model.Permission;
import cn.tedu.straw.commons.model.Role;
import cn.tedu.straw.commons.model.User;
import cn.tedu.straw.sys.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE username=#{username}")
    public User findUserByUsername(String username);

    @Select("select p.id,p.name " +
            "FROM user u " +
            "left join user_role ur on u.id=ur.user_id " +
            "LEFT JOIN role r on r.id=ur.role_id " +
            "LEFT JOIN role_permission rp on r.id=rp.role_id " +
            "LEFT JOIN permission p on p.id=rp.permission_id " +
            "where u.id=#{id}")
    List<Permission> findUserPermissionById(Integer id);

    @Select("SELECT id,username,nick_name FROM user where username=#{username}")
    UserVO getUserVoByUsername(String username);

    /**
    * 根據用戶的ID查詢其全部的角色訊息
    */
    @Select("SELECT r.id,r.name " +
            "FROM user u " +
            "LEFT JOIN user_role ur ON ur.user_id=u.id " +
            "LEFT JOIN role r ON r.id=ur.role_id " +
            "WHERE u.id=#{userId}")
    List<Role> findUserRolesById(Integer userId);
}
