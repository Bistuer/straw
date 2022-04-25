package cn.tedu.straw.kafka.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 测试类！！！！！！！！！
 * @author fanzhen
 */
@Data
@Accessors(chain = true)
public class DemoMessage implements Serializable {

    private String content;
    private Integer id;
    private Long time;
}
