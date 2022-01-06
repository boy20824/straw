package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.AnswerVO;
import cn.tedu.straw.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@RestController
@RequestMapping("/v1/answers")
@Slf4j
public class AnswerController {

    @Autowired
    IAnswerService iAnswerService;

    @PostMapping("")
    @PreAuthorize("hasRole('TEACHER')")
    public R postAnswer(@Validated AnswerVO answerVO, BindingResult result, @AuthenticationPrincipal User user){

        if(result.hasErrors()){
            String message=result.getFieldErrors().toString();
            return R.unprocessableEntity(message);
        }
        log.debug("表單數據{}",answerVO);

        Answer answer=iAnswerService.saveAnswer(answerVO, user.getUsername());

        return R.created(answer);
    }

    /**
     * 請求路徑/v1/answers/question/157
     * 根據問題的編號獲取全部回答
     * @param questionId 問題編號
     * @return 該問題全部編號
     */
    @GetMapping("/question/{questionId}")
    public R<List<Answer>> questionAnswers(@PathVariable Integer questionId){

        if(questionId==null){
            throw new ServiceException("問題ID不得為空!");
        }

        List<Answer> answers=iAnswerService.getAnswersByQuestionId(questionId);

        return R.ok(answers);
    }

    @GetMapping("/{answerId}/solved")
    public R solved(@PathVariable Integer answerId){
        log.debug("收到answerId {}",answerId);
        boolean pass =iAnswerService.accept(answerId);
        if(pass){
            return R.accepted("成功!");
        }else{
            return R.notFound("已經採納過了!");
        }
    }


}
