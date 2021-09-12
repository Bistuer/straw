package cn.tedu.straw.search.mapper;

import cn.tedu.straw.search.bean.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemRepository extends ElasticsearchRepository<Item, Long> {
}
