package cn.tedu.straw.portal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    //已經發佈待解決
    public static final Integer POSTED=0;
    //老師已經回覆,正在解決中
    public static final Integer SOLVING=1;
    //已經接收答案,已經解決問題
    public static final Integer SOLVED=2;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 正文
     */
    @TableField("content")
    private String content;

    /**
     * 作者id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 作者昵称
     */
    @TableField("user_nick_name")
    private String userNickName;

    /**
     * 状态，0-未回复，1-未解决，2-已解决
     */
    @TableField("status")
    private Integer status;

    /**
     * 点击量
     */
    @TableField("hits")
    private Integer hits;

    @TableField("tag_names")
    private String tagNames;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("modify_time")
    private LocalDateTime modifyTime;

    @TableField("public_status")
    private Integer publicStatus;

    @TableField("delete_status")
    private Integer deleteStatus;

    /*
    當前問題的標籤列表
    不是數據庫儲存的數據 @TableField(exist = false)
     */
    @TableField(exist = false)
    private List<Tag> tags;


}
