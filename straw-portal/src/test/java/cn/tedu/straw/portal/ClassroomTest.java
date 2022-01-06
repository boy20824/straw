package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.ClassroomMapper;
import cn.tedu.straw.portal.model.Classroom;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClassroomTest {

    @Autowired
    ClassroomMapper classroomMapper;

    @Test
    public void queryWapper(){
        //利用QueryWapper創建查詢條件
        QueryWrapper<Classroom> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("invite_code","JSD1912-876840");
        //執行MybatisPlus提供的方法selectOne動態生成SQL執行查詢
        Classroom classroom=classroomMapper.selectOne(queryWrapper);
        System.out.println(classroom);
    }
}
