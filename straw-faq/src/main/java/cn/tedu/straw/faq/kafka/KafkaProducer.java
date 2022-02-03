package cn.tedu.straw.faq.kafka;

import cn.tedu.straw.commons.kafka.Topics;
import cn.tedu.straw.commons.model.Question;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 負責向Kafka發送數據
 */
@Component
@Slf4j
public class KafkaProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new Gson();

    public void sendQuestion(Question question) {
        String json = gson.toJson(question);
        log.debug("發送問題數據 : {}", json);
        kafkaTemplate.send(Topics.QUESTIONS, json);
    }
}
