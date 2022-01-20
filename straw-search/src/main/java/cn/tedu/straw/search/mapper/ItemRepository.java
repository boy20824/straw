package cn.tedu.straw.search.mapper;

import cn.tedu.straw.search.vo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
/*
ItemRepository 要繼承 ElasticsearchRepository 在泛型上約定 實體類型,跟id類型
ItemRepository 就會自動從ElasticsearchRepository繼承全部的CRUD方法
 */
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {
}
