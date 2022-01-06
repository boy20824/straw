package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {

    /**
     * 根據問題編號查詢所有答案
     * 運用關聯查詢,查出每個答案附帶的全部評論
     * @param questionId 問題的ID
     * @return 問題全部答案附帶全部評論
     */
    List<Answer> findAnswersByQuestionId(Integer questionId);

    @Update("update answer set accepted_status=#{acceptedStatus} " +
            "  where id =#{answerId}")
    Integer updateAcceptedStatus(@Param("answerId")Integer answerId,
                                 @Param("acceptedStatus") Integer acceptedSataus);
}
