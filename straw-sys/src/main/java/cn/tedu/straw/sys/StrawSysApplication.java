package cn.tedu.straw.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.straw.sys.mapper")
public class StrawSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrawSysApplication.class, args);
    }

}
