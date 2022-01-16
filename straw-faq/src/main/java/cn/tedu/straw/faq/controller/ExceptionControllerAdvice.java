package cn.tedu.straw.faq.controller;

import cn.tedu.straw.commons.service.ServiceException;
import cn.tedu.straw.commons.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
/**
 * 統一的異常處理
 */
public class ExceptionControllerAdvice {

    /**
     * ServiceException處理
     * @param e
     * @return
     */
    @ExceptionHandler
    public R handleServiceException(ServiceException e){
    log.debug("處理業務異常");
    log.error("業務異常",e);
    return R.failed(e);
    }

    /**
     * 處理其他異常
     * @param e
     * @return
     */
    @ExceptionHandler
    public R handleException(Exception e){
        log.error("其他異常",e);
        return R.failed(e);
    }
}
