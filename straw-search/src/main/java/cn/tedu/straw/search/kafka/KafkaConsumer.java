package cn.tedu.straw.search.kafka;

import cn.tedu.straw.commons.kafka.Topics;
import cn.tedu.straw.search.service.IQuestionService;
import cn.tedu.straw.search.vo.QuestionVO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class KafkaConsumer {

    private Gson gson=new Gson();

    @Resource
    private IQuestionService iQuestionService;

    @KafkaListener(topics = Topics.QUESTIONS)
    public void receiveQuestion(ConsumerRecord<String,String> record){
        String json=record.value();
        log.debug("收到數據:{}",json);
        QuestionVO questionVO=gson.fromJson(json,QuestionVO.class);
        log.debug("轉換為:{}",questionVO);
        iQuestionService.saveQuestion(questionVO);
    }
}
