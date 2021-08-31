package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    /**
     * ConcurrentHashMap 线程安全的map 适合在高并发的环境下使用
     */
    private final Map<String, Tag> map = new ConcurrentHashMap<>();

    /**
     * {
     *   "code": 200,
     *   "message": "OK",
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "Java基础",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T14:39:48"
     *     },
     *     {
     *       "id": 2,
     *       "name": "Java OOP",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:30:09"
     *     },
     *     {
     *       "id": 3,
     *       "name": "Java SE",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:32:13"
     *     },
     *     {
     *       "id": 4,
     *       "name": "WebServer",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:32:50"
     *     },
     *     {
     *       "id": 5,
     *       "name": "二进制",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:33:18"
     *     },
     *     {
     *       "id": 6,
     *       "name": "Web",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:33:58"
     *     },
     *     {
     *       "id": 7,
     *       "name": "MySQL",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:34:20"
     *     },
     *     {
     *       "id": 8,
     *       "name": "Servlet",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:34:40"
     *     },
     *     {
     *       "id": 9,
     *       "name": "Spring",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:34:58"
     *     },
     *     {
     *       "id": 10,
     *       "name": "SpringMVC",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:35:17"
     *     },
     *     {
     *       "id": 11,
     *       "name": "MyBatis",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:35:38"
     *     },
     *     {
     *       "id": 12,
     *       "name": "Ajax",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:36:02"
     *     },
     *     {
     *       "id": 13,
     *       "name": "SpringBoot",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:36:22"
     *     },
     *     {
     *       "id": 14,
     *       "name": "SpringCloud",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:36:43"
     *     },
     *     {
     *       "id": 15,
     *       "name": "面试题",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:37:28"
     *     },
     *     {
     *       "id": 16,
     *       "name": "搜索引擎",
     *       "createby": "admin",
     *       "createtime": "2020-03-09T23:40:47"
     *     },
     *     {
     *       "id": 17,
     *       "name": "Docker",
     *       "createby": "admin",
     *       "createtime": "2020-03-10T17:19:05"
     *     },
     *     {
     *       "id": 18,
     *       "name": "Linux",
     *       "createby": "admin",
     *       "createtime": "2020-03-16T14:44:04"
     *     },
     *     {
     *       "id": 19,
     *       "name": "CentOS",
     *       "createby": "admin",
     *       "createtime": "2020-03-16T14:44:22"
     *     },
     *     {
     *       "id": 20,
     *       "name": "Dubbo",
     *       "createby": "admin",
     *       "createtime": "2020-03-19T09:52:09"
     *     }
     *   ]
     * }
     * @return
     */
    @Override
    public List<Tag> getTags() {
        //这个if主要是为了保证tags被顺利赋值之后的高效运行
        if (tags.isEmpty()) {
            synchronized (tags) {
                //这个if主要是为了保证不会有两条以上线程为tags重复添加内容
                if (tags.isEmpty()) {
                    //super.list() 是父类ServiceImpl提供的查询当前指定实体类全部行的代码
                    tags.addAll(super.list());
                    //为所有标签赋值List类型之后,可以同步给map赋值
                    for (Tag t : tags) {
                        //将tag中所有标签赋值给map
                        //而map的key是tag的name,value就是tag
                        //map结果:如JAVA基础(key) value:1,java基础,admin,2020-03-09 14:39:48
                        map.put(t.getName(), t);
                    }
                }

            }
        }
        return tags;
    }

    @Override
    public Map<String, Tag> getName2TagMap() {
        //判断如果map是空,证明上面getTag方法没有运行
        if (map.isEmpty()) {
            //那么就调用上面的getTag方法
            getTags();
        }
        return map;
    }

}
