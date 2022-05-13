package com.superb.upetstar.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hym
 * @description
 */
public interface OssService {
    /**
     * 上传文件
     */
    String uploadFile(MultipartFile file);
}
