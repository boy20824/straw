package cn.tedu.straw.search;

import cn.tedu.straw.search.mapper.ItemRepository;
import cn.tedu.straw.search.vo.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class ItemRepositoryTest {

    @Resource
    ItemRepository itemRepository;

    @Test
    public void save() {
        Item item = new Item()
                .setId(1L)
                .setTitle("小米k20手機")
                .setCategory("小米")
                .setCategory("手機")
                .setImage("images/1.png")
                .setPrice(2345.00);
        Item obj = itemRepository.save(item);
        log.debug("{}", obj);
    }

    @Test
    public void addItem() {

        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(2L, "小米手機10", "手機", "小米", "2.png", 2000.00));
        itemList.add(new Item(3L, "小米手機6", "手機", "小米", "3.png", 1000.00));
        itemList.add(new Item(4L, "槌子手機6I", "手機", "槌子", "5.png", 1800.00));
        itemList.add(new Item(5L, "華為Meta30", "手機", "華為", "8.png", 3888.00));
        itemList.add(new Item(6L, "華為手機Note1", "手機", "華為", "10.png", 2888.00));
        itemList.add(new Item(7L, "Mate40透明手機殼", "手機配件", "華為", "100.png", 20.00));
        itemRepository.saveAll(itemList);
    }

    @Test
    public void findAll() {
        Iterable<Item> itemIterable = itemRepository.findAll();
        itemIterable.forEach(item -> log.debug("{}", item));
    }

    @Test
    public void queryItemsByTitleMatches() {
        Iterable<Item> items = itemRepository.queryItemsByTitleMatches("小米手機");
        items.forEach(item -> log.debug("{}", item));
    }

    @Test
    public void queryItemsByTitleMatchesAndBandMatchesOrderByPriceAsc() {
        Iterable<Item> items = itemRepository.queryItemsByTitleMatchesAndBandMatchesOrderByPriceAsc("手機", "小米");
        items.forEach(item -> log.debug("{}", item));
    }

    @Test
    public void pageable() {
        int pageNum = 1;//Spring 中的頁號從0開始
        int pageSize = 3;//頁面大小,每頁顯示的行數
        Page<Item> page = itemRepository.queryItemsByTitleMatchesAndBandMatchesOrderByPriceAsc(
                "手機", "小米", PageRequest.of(pageNum, pageSize));
        //顯示當前數據
      page.getContent().forEach(item -> log.debug("{}",item));
      //顯示分頁數據
        log.debug("當前頁號:{}",page.getNumber());
        log.debug("頁面大小:{}",page.getSize());
        log.debug("是否是首頁:{}",page.isFirst());
        log.debug("是否是最後一頁:{}",page.isLast());
        log.debug("前一頁號:{}",page.previousOrFirstPageable().getPageNumber());
        log.debug("後一頁號:{}",page.nextOrLastPageable().getPageNumber());
    }


}
