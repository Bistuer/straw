package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     *查询所有的Tag
     * @return 所有的Tag
     */
    public abstract List<Tag> getTags();
}
