package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.R;
import cn.tedu.straw.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
public class SystemController {

    @Autowired
    IUserService iUserService;

    //讀取自定義配置訊息
    @Value("${straw.resource.path}")
    private File resourcePath;

    @Value("${straw.resource.host}")
    private String resourceHost;

    @GetMapping("/login.html")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/register.html")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    /*
    加上@Validated開啟對RegisterVo對象進行驗證,驗證結果會自動儲存到BindingResult對象中
     validateResult.hasErrors()  用於檢查有無驗證錯誤
     validateResult.getFieldError().getDefaultMessage() 用於獲取驗證期間出現的錯誤訊息
     */
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

    @PostMapping("/upload/file")
    public R uploadFile(MultipartFile imageFile) throws IOException {
        String path = "D:/upload";
        File folder = new File(path);
        folder.mkdir();
        //展示收到的文件名
        String fileName = imageFile.getOriginalFilename();
        //在upload文件中創建文件
        File file = new File(folder, fileName);
        imageFile.transferTo(file);
        return R.ok("成功保存文件");
    }

    @PostMapping("/upload/image")
    public R uploadImage(MultipartFile imageFile) throws IOException {
        //創建目標儲存目錄
        String path = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());
        File folder = new File(resourcePath, path);
        folder.mkdirs();
        log.debug("存儲文件夾:{}", folder.getAbsolutePath());
        //獲取副檔名
        String filename = imageFile.getOriginalFilename();
        log.debug("上傳文件名:{}", filename);
        String ext = filename.substring(filename.lastIndexOf('.'));
        log.debug("副檔名:{}", ext);

        //生成隨機的文件名
        String name = UUID.randomUUID().toString() + ext;
        File file = new File(folder, name);
        log.debug("保存到:{}", file.getAbsolutePath());

        //保存文件
        imageFile.transferTo(file);

        //文件顯示的url
        String url = resourceHost + "/" + path + "/" + name;
        log.debug("Image URL{}", url);

        return R.ok(url);
    }
}
