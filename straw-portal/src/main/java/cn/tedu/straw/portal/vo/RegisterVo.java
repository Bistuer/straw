package cn.tedu.straw.portal.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册表实体类参数
 *
 * @author fanzhen
 * @Date 2021.6.8
 */
@Data
public class RegisterVo implements Serializable {

    /**
     * 注册表实体类参数：
     * inviteCode 邀请码
     * nickname 昵称
     * confirm 再次确认密码
     */
    private String inviteCode;
    private String phone;
    private String nickname;
    private String password;
    private String confirm;


}
