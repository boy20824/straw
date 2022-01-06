package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 查詢與老師有關的問題列表,包含老師發布的問題和請老師解決的問題
     * @param userId 老師的用戶ID
     * @return 與老師有關的問題列表
     */
    @Select("SELECT q.* " +
            "FROM question q " +
            "LEFT JOIN user_question uq ON q.id=uq.question_id " +
            "WHERE q.user_id=#{userId} OR uq.user_id=#{userId} " +
            "ORDER BY q.modify_Time DESC")
    List<Question> findTeachersQuestions(Integer userId);

    @Update("UPDATE question set status=#{status} where id =#{questionId}")
    Integer updateStatus(@Param("questionId") Integer questionId,
                         @Param("status") Integer status);
}
