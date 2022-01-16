package cn.tedu.straw.commons.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //聲明用戶的type,1是老師,0是學生
    public static final Integer MASTER=1;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名，学生注册时，使用手机号码作为用户名
     */
    @TableField("username")
    private String username;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别，0-女，1-男
     */
    @TableField("gender")
    private Integer gender;

    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;

    @TableField("classroom_id")
    private Integer classroomId;

    @TableField("enabled")
    private Integer enabled;

    @TableField("locked")
    private Integer locked;

    /**
     * 账号类型，0-学生，1-老师
     */
    @TableField("account_type")
    private Integer accountType;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("modify_time")
    private LocalDateTime modifyTime;

    @TableField("self_introduction")
    private String selfIntroduction;


}
