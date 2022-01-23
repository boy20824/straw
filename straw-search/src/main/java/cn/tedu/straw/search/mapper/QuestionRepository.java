package cn.tedu.straw.search.mapper;

import cn.tedu.straw.search.vo.QuestionVO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends ElasticsearchRepository<QuestionVO,Integer> {


}
