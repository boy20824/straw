package cn.tedu.straw.kafka.service;

import cn.tedu.straw.kafka.vo.DemoMessage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 監聽接收kafka的數據
 */
@Component
@Slf4j
public class DemoConsumer {

    private Gson gson=new Gson();

    @KafkaListener(topics = "MyTopic")
    public void receive(ConsumerRecord<String,String>record){
        //record代表從kafka中收到數據消息,其中value值就是收到json數據
        String json=record.value();
        log.debug("收到: {}",json);
        DemoMessage message=gson.fromJson(json,DemoMessage.class);
        log.debug("收到對像: {}",message);
    }
}
