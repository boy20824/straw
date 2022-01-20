package cn.tedu.straw.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StrawSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrawSearchApplication.class, args);
    }

}
