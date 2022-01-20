package cn.tedu.straw.search;

import cn.tedu.straw.search.mapper.ItemRepository;
import cn.tedu.straw.search.vo.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class ItemRepositoryTest {

    @Resource
    ItemRepository itemRepository;

    @Test
    public void save(){
        Item item   =new Item()
        .setId(1L)
        .setTitle("小米k20手機")
        .setCategory("小米")
        .setCategory("手機")
        .setImage("images/1.png")
        .setPrice(2345.00);
        Item obj=itemRepository.save(item);
        log.debug("{}",obj);
    }
}
