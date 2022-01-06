package cn.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AnswerVO implements Serializable {

    @NotNull(message = "問題id不得為空")
    private Integer questionId;

    @NotBlank(message = "回覆正文不能為空")
    private String content;
}
