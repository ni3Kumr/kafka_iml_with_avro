package com.javatechie.consumer;

import com.javatechie.dto.Employee;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

@Service
public class KafkaAvroConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(KafkaAvroConsumer.class);

    @RetryableTopic(attempts = "4")
    @KafkaListener(topics = "${topic.name}")
    public void read(ConsumerRecord<String, Employee> consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC)String topic,@Header(KafkaHeaders.OFFSET)long offset) {
        String key = consumerRecord.key();
        Employee employee = consumerRecord.value();

        if(employee.getAge() <21){
            throw  new RuntimeException("You are not authorise to work here");

        }
        log.info("Avro message received for key : {} value : {}  topic : {} offset:{}", key, employee.toString(),topic,offset);

    }

    @DltHandler
    public void listenerDTL(ConsumerRecord<String, Employee> consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC)String topic,@Header(KafkaHeaders.OFFSET)long offset){

        log.info("DLT received :{} from : {} offset:{}",consumerRecord.value().getFirstName(),topic,offset);
    }


}
