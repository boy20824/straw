package cn.tedu.straw.search.controller;

import cn.tedu.straw.commons.vo.R;
import cn.tedu.straw.search.service.IQuestionService;
import cn.tedu.straw.search.vo.QuestionVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/questions")
@Slf4j
public class QuestionController {

    @Resource
    private IQuestionService iQuestionService;

    /**
     * 請求路徑 http://localhost:9000/search/v1/questions
     */
    @PostMapping
    public R<QuestionVO> search(String key, Integer pageNum,
                                @AuthenticationPrincipal UserDetails userDetails) {
        if (key == null) {
            key = "";
        }
        if (pageNum == null) {
            pageNum = 1;
        }

        int pageSize = 8;
        PageInfo<QuestionVO> pageInfo = iQuestionService.search(key, userDetails.getUsername(), pageNum, pageSize);

        return R.ok(pageInfo);

    }
}
