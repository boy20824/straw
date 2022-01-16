package cn.tedu.straw.resource.controller;

import cn.tedu.straw.commons.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/v1/images")
public class ImageController {

    //讀取自定義配置訊息
    @Value("${spring.resources.static-locations}")
    private File resourcePath;

    @Value("${straw.resource.host}")
    private String resourceHost;

    @PostMapping
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
