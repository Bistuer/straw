package cn.tedu.straw.search;

import cn.tedu.straw.search.bean.Item;
import cn.tedu.straw.search.mapper.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import javax.annotation.Resource;

@SpringBootTest
class StrawSearchApplicationTests {

    @Resource
    ElasticsearchOperations elasticsearchOperations;

    @Test
    void contextLoads() {
        System.out.println(elasticsearchOperations);
    }

    @Resource
    ItemRepository itemRepository;

    @Test
    void addItem() {
        Item item = new Item(1L, "华为Mate40", "手机"
                , "华为", 4890.0, "/image/11.jpg");
        itemRepository.save(item);
    }

    @Test
    void getById() {
        Object item = itemRepository.findById(1L);
        System.out.println(item);
    }


}
