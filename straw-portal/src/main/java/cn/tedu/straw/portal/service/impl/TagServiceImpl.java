package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
