package cn.tedu.straw.portal.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 注册表实体类参数
 *
 * @author fanzhen
 * @Date 2021.6.8
 */
@Data
public class RegisterVo implements Serializable {

    private static final long serialVersionUID = -1845512208779670837L;

    /**
     * 注册表实体类参数：
     * inviteCode 邀请码
     * nickname 昵称
     * confirm 再次确认密码
     *
     * @NotBlank 只能作用在String上, 不能为null, 去掉空格之后也不能为"" ; message是为空时报出的异常
     * @Pattern 表示下面的属性需要通过指定正则表达式的判断
     */
    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    private String phone;

    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^.{2,20}$", message = "昵称在2到20位之间")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^\\w{6,20}$", message = "密码在6~20位之间")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String confirm;

}
