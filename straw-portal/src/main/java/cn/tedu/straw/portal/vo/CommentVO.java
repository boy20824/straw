package cn.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CommentVO implements Serializable {

    @NotNull(message ="答案ID不能為空" )
    private Integer answerId;

    @NotBlank(message = "問題內容不得為空")
    private String content;
}
