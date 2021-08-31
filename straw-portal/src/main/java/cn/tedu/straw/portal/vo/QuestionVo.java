package cn.tedu.straw.portal.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 发布问题的业务处理建立一个问题VO实体类
 *
 * @author fanzhen
 * @Date 2021/8/31
 */
@Data
public class QuestionVo {

    @NotBlank(message = "标题不能为空")
    @Pattern(regexp = "^.{3,50}$", message = "标题长度在3~50个字符之间")
    private String title;

    private String[] tagNames = {};
    private String[] teacherNickNames = {};

    @NotBlank(message = "问题内容不能为空")
    private String content;

}
