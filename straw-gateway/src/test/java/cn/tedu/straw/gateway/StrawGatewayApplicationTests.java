package cn.tedu.straw.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootTest
class StrawGatewayApplicationTests {

    @Resource
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        String url = "http://sys-service/v1/auth/demo";
        String str = restTemplate.getForObject(url, String.class);
        System.err.println(str);
    }

}
