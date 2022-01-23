package cn.tedu.straw.search.vo;

import cn.tedu.straw.commons.model.Tag;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用於封裝存儲到Elasticsearch中的問題數據
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document(indexName = "questions")
public class QuestionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //已經發佈待解決
    public static final Integer POSTED=0;
    //老師已經回覆,正在解決中
    public static final Integer SOLVING=1;
    //已經接收答案,已經解決問題
    public static final Integer SOLVED=2;


    @Id
    private Integer id;

    /**
     * 标题
     */
    @Field(type = FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_smart")
    private String title;

    /**
     * 正文
     */
    @Field(type = FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_smart")
    private String content;

    /**
     * 作者id
     */
   @Field(type = FieldType.Integer)
    private Integer userId;

    /**
     * 作者昵称
     */
    @Field(type = FieldType.Keyword)
    private String userNickName;

    /**
     * 状态，0-未回复，1-未解决，2-已解决
     */
    @Field(type = FieldType.Integer)
    private Integer status;

    /**
     * 点击量
     */
    @Field(type = FieldType.Integer)
    private Integer hits;

    @Field(type = FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_smart")
    private String tagNames;

    /*
    創建時間
    時間類型必須指定格式,否則不能保存
     */
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private LocalDateTime createTime;

    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private LocalDateTime modifyTime;

    @Field(type = FieldType.Integer)
    private Integer publicStatus;

    @Field(type = FieldType.Integer)
    private Integer deleteStatus;

    /*
    當前問題的標籤列表
    不是數據庫存儲的數據,標註@Transient註解以後,將不保存到ES中
     */
    @Transient
    private List<Tag> tags;


}
