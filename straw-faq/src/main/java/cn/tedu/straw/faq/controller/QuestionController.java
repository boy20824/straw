package cn.tedu.straw.faq.controller;


import cn.tedu.straw.commons.model.Question;
import cn.tedu.straw.faq.service.IQuestionService;
import cn.tedu.straw.commons.service.ServiceException;
import cn.tedu.straw.faq.vo.QuestionVO;
import cn.tedu.straw.commons.vo.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@RestController
@RequestMapping("/v1/questions")
@Slf4j
public class QuestionController {

    @Autowired
    private IQuestionService iQuestionService;

    /**
     * 獲取當前用戶的全部問題
     * 請求URL:/v1/questions/my
     *
     * @return 當前用戶全部問題
     */
    @GetMapping("/my")
    public R<PageInfo<Question>> my(Integer pageNum,@AuthenticationPrincipal UserDetails userDetails) {

        if (pageNum == null) {
            pageNum = 1;
        }
        Integer pageSize = 8;
        try {
            log.debug("開始請求當前用戶全部問題");
            PageInfo<Question> pageInfo = iQuestionService.getMyQuestions(userDetails.getUsername(),pageNum, pageSize);
            return R.ok(pageInfo);
        } catch (ServiceException e) {
            log.debug("失敗的加載當前用問的問題{}", e);
            return R.failed(e);
        }
    }

    /**
     * 請求url:POST /v1/questions
     */
    @PostMapping("")
    public R<String> createQuestion(@Validated QuestionVO questionVO, BindingResult result, @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return R.unprocessableEntity(message);
        }
        if (questionVO.getTagNames().length == 0) {
            return R.unprocessableEntity("標籤名稱不能為空");
        }
        if (questionVO.getTeacherNicknames().length == 0) {
            return R.unprocessableEntity("沒有選擇答題烙施");
        }
        log.debug("收到表單訊息{}", questionVO);

        iQuestionService.saveQuestion(userDetails.getUsername(),questionVO);
        return R.created("成功保存問題數據");
    }

    /**
     * 請求 /v1/questions/teacher 分頁返回當前老師有關的問題
     * @param user 當前老師
     * @param pageNum 頁號
     * @return 一頁數據
     */
    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public R<PageInfo<Question>> teachers(@AuthenticationPrincipal User user,Integer pageNum) {
        if (pageNum == null) {
            pageNum = 1;
        }
        Integer pageSize = 8;
        PageInfo<Question> pageInfo = iQuestionService.getQuestionsByTeacherName(user.getUsername(), pageNum, pageSize);
        return R.ok(pageInfo);
    }

    /**
     * Rest 風格的API,請求路徑是 /v1/questions/156
     * 對象的ID通過URL傳遞,{id}是URL參數佔位符,
     * @param id 問題的id,利用@PathVariable從URL獲取
     * @return
     */
    @GetMapping("/{id}")
    public R<Question> question(@PathVariable Integer id){
        if(id==null){
            R.invalidRequest("ID不能為空!!");
        }
        log.debug("id{}",id);
        Question question=iQuestionService.getQuestionsById(id);
        return  R.ok(question);
    }
    
    @GetMapping("/count")
    public Integer count(Integer userId){
        Integer count=iQuestionService.countQuestionsByUserId(userId);
        return count;
    }

    /*
    Rest API 用於將Question數據導到straw-search中
    分頁導出數據,用於返回數據頁數
     */
    @GetMapping("/page/count")
    public Integer pageCount(Integer pageSize){
        Integer rows=iQuestionService.count();
        return rows%pageSize==0?rows/pageSize:rows/pageSize+1;
    }

    /*
    用於返回一頁數據
     */
    @GetMapping("/page")
    public List<Question> page(Integer pageNum, Integer pageSize){
        PageInfo<Question> pageInfo=iQuestionService.getQuestions(pageNum,pageSize);
        return pageInfo.getList();
    }
}
