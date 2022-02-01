package cn.tedu.straw.kafka.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DemoMessage {
    private String content;
    private Integer id;
    private Long time;
}
