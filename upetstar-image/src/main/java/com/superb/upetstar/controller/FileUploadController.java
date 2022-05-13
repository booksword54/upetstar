package com.superb.upetstar.controller;

import com.superb.upetstar.common.pojo.entity.Image;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.service.IImageService;
import com.superb.upetstar.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hym
 * @description
 */
@Api(tags = "文件上传相关")
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Resource
    private OssService ossService;

    @Resource
    private IImageService imageService;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String url = ossService.uploadFile(file);
        imageService.save(new Image().setUrl(url));
        return Result.success().data("url", url);
    }

}
