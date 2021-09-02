package cn.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 开发用户信息面板实体类
 * Accessors(chain = true) 支持连缀书写
 *
 * @author fanzhen
 * @Date 2021/9/2
 */
@Data
@Accessors(chain = true)
public class UserVo {

    private Integer id;
    private String username;
    private String nickname;

    /*
      两个面板中显示的数据
      问题数量
     */
    private int questions;

    /*
      收藏数量
     */
    private int collections;
}
