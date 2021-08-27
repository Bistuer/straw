package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
public interface ITagService extends IService<Tag> {

    /**
     * 查询所有的Tag
     * @return 所有的Tag
     */
    public abstract List<Tag> getTags();


    /**
     * 获得所有标签的方法，返回Map的方法
     * @return String- Java基础  Tag对应一个tag对象 如：id 1 name Java基础 createby admin createtime 2020-03-09 14:39:48
     */
    Map<String,Tag> getName2TagMap();
}
