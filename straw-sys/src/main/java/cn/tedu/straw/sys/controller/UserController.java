package cn.tedu.straw.sys.controller;

import cn.tedu.straw.commons.vo.R;
import cn.tedu.straw.sys.service.IUserService;
import cn.tedu.straw.sys.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {

    @Resource
    private IUserService iUserService;

    /*
    加上@Validated開啟對RegisterVo對象進行驗證,驗證結果會自動儲存到BindingResult對象中
     validateResult.hasErrors()  用於檢查有無驗證錯誤
     validateResult.getFieldError().getDefaultMessage() 用於獲取驗證期間出現的錯誤訊息
     */
    @PostMapping("/register")
    public R registerStudent(@Validated RegisterVo registerVo, BindingResult validateResult) {
        log.debug("收到表單數據{}", registerVo);
        //檢查驗證結果是否有錯誤
        if (validateResult.hasErrors()) {
            //取得驗證錯誤
            String error = validateResult.getFieldError().getDefaultMessage();
            log.info("表單驗證錯誤{}", error);
            return R.unprocessableEntity(error);
        }
        if (!registerVo.getPassword().equals(registerVo.getConfirm())) {
            log.info("確認密碼不一致");
            return R.unprocessableEntity("確認密碼不一致");
        }

        iUserService.registerStudent(registerVo);
        return R.created("成功註冊!!");
    }
}
