package cn.tedu.straw.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author fanzhen
 * @EnableKafka 使用kafka的注释
 * @EnableScheduling计时器功能
 */

@SpringBootApplication
@EnableKafka
@EnableScheduling
public class StrawKafkaApplication {


    public static void main(String[] args) {
        SpringApplication.run(StrawKafkaApplication.class, args);
    }


}
