package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.service.ITagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    /**
     * 相比于线程不安全的ArrayList  CopyOnWriteArrayList是一种线程安全的集合适合在高并发的环境下使用
     */
    private final List<Tag> tags = new CopyOnWriteArrayList<>();

    @Override
    public List<Tag> getTags() {

        //这个if主要是为了保证tags被顺利赋值之后的高效运行
        if (tags.isEmpty()) {
            synchronized (tags) {
                //这个if主要是为了保证不会有两条以上线程为tags重复添加内容
                if (tags.isEmpty()) {
                    //super.list() 是父类提供的查询当前指定实体类全部行的代码
                    tags.addAll(super.list());
                }

            }
        }

        return tags;

    }

}
