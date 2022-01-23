package cn.tedu.straw.search.mapper;

import cn.tedu.straw.search.vo.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
/*
ItemRepository 要繼承 ElasticsearchRepository 在泛型上約定 實體類型,跟id類型
ItemRepository 就會自動從ElasticsearchRepository繼承全部的CRUD方法
 */
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    /**
     * 搜索title中包含相關關鍵字的Item對象
     * @param title title中的關鍵字,會自動進行分詞檢索
     * @return title包含關鍵字的Item對象
     */
    Iterable<Item> queryItemsByTitleMatches(String title);

    /*
    查詢某個品牌下面的全部包含指定的title商品
     */
    Iterable<Item> queryItemsByTitleMatchesAndBandMatchesOrderByPriceAsc(String title,String Band);

    Page<Item> queryItemsByTitleMatchesAndBandMatchesOrderByPriceAsc(
            String title, String Band, Pageable pageable);
}
