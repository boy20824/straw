package cn.tedu.straw.kafka.service;

import cn.tedu.straw.kafka.vo.DemoMessage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息的生產者,就是消息的發送者
 */
@Component
@Slf4j
public class DemoProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new Gson();

    /**
     * 利用spring安排了一個計劃執行功能
     * 每間隔10秒執行一次sendMessage方法痾
     */
    @Scheduled(fixedRate = 1000*10)
    public void sendMessage(){
        DemoMessage message=new DemoMessage()
                .setContent("您好")
                .setId(100)
                .setTime(System.currentTimeMillis());
        log.debug("發送數據{}",message);
        //將需要發送的數據轉換為JSON格式進行發送
        String json=gson.toJson(message);
        kafkaTemplate.send("MyTopic",json);

    }
}
