package cn.tedu.straw.search.mapper;

import cn.tedu.straw.search.vo.QuestionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends ElasticsearchRepository<QuestionVO, Integer> {

    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"must\": [{\n" +
            "          \"bool\": {\n" +
            "            \"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?1\"}}]\n" +
            "          }\n" +
            "        }, {\n" +
            "          \"bool\": {\"should\": [{\"term\": {\"publicStatus\": 1}}, {\"term\": {\"userId\": ?2}}]\n" +
            "          }\n" +
            "        }]\n" +
            "    }\n" +
            "  }")
    public Page<QuestionVO> queryAllByParam(String title, String content, Integer userId, Pageable pageable);

}
