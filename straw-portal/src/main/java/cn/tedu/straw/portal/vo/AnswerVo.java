package cn.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 完成讲师回复问题功能创建AnswerVo
 *
 * @author fanzhen
 */
@Data
@Accessors(chain = true)
public class AnswerVo implements Serializable {

    @NotNull(message = "问题编号不能为空")
    private Integer questionId;

    @NotBlank(message = "回复内容不能为空")
    private String content;

}
