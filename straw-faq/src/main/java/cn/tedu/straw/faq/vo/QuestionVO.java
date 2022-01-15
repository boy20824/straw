package cn.tedu.straw.faq.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class QuestionVO implements Serializable {

    @NotBlank(message = "問題標題不得為空")
    @Pattern(regexp = "^.{3,50}$", message = "標題3到50個字符")
    private String title;
    //防止空值異常
    private String[] tagNames = {};
    private String[] teacherNicknames = {};
    @NotBlank(message = "問題內容不得為空")
    private String content;
}
