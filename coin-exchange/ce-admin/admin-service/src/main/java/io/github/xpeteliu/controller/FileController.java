package io.github.xpeteliu.controller;

import io.github.xpeteliu.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Api(tags = "Controller for file uploading")
public class FileController {

    @PostMapping("/image/AliYunImgUpload")
    @ApiOperation("Receive an uploaded image file")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "file to be uploaded")
    })
    public R<String> fileUpload(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return R.failure("", "Uploaded file is empty");
            }

            // TODO: store the files in OSS server

            String destPath = "E:/images/" + file.getOriginalFilename();
            File dest = new File(destPath);
            dest.createNewFile();
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
            return R.success("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png", "File uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            return R.failure("", e.getMessage());
        }
    }
}
