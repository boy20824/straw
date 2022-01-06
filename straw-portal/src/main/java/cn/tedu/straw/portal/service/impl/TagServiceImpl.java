package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
 * @since 2021-11-14
 */
@Service
@Slf4j
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    /*
    CopyOnWriteArrayList 是線程安全的List,適合併發訪問場合
     */
    private final List<Tag> tags = new CopyOnWriteArrayList<>();

    /*
    ConcurrentHashMap 是線程安全的List,適合併發訪問場合
     */
    private final Map<String, Tag> name2Tag = new ConcurrentHashMap<>();

    @Override
    public List<Tag> getTags() {
        if (tags.isEmpty()) {
            synchronized (tags) {
                if (tags.isEmpty()) {
                    //list()是Mybatis Plus提供的方法,在ServiceImpl中定義
                    //也就是繼承於ServiceImpl的方法,方法的作用是返回數據庫中Tag對象
                    tags.addAll(list());
                    tags.forEach(tag -> name2Tag.put(tag.getName(), tag));
                    log.debug("加載了Map{}", name2Tag);
                    log.debug("加載tag列表{}", tags);
                }
            }
        }

        return tags;
    }

    @Override
    public Map<String, Tag> getName2TagMap() {
        if (name2Tag.isEmpty()) {
            getTags();
        }
        return name2Tag;
    }
}
