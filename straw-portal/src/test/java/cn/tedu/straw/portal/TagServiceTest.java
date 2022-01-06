package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.service.ITagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class TagServiceTest {

    @Autowired
    ITagService iTagService;

    @Test
    public void getTags(){
        List<Tag> list=iTagService.getTags();
        list.forEach(tag-> System.out.println(tag));
    }

    @Test
    public void getName2TagMap(){
        Map<String,Tag> map=iTagService.getName2TagMap();
        map.forEach((name,tag)-> System.out.println(name+":"+tag));
    }
}
