package com.superb.upetstar.upsimage.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.superb.upetstar.upsimage.utils.ConstantPropertiesUtils;
import com.superb.upetstar.service.OssService;
import org.apache.dubbo.config.annotation.DubboService;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author hym
 * @description
 */
@DubboService
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFile(MultipartFile file) {
        // 工具类获取值
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流
            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();

            // 获取文件名称
            String filename = file.getOriginalFilename();

            // 1、在文件名中添加随机且唯一的值
            // y6g1-jo09-yiw4 --> y6g1jo09yiw4
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid + filename;

            // 2、把文件按照日期进行分类
            // 2019/11/12/01.jpg
            // 获取当前日期 joda-time
            String datePath = new DateTime().toString("yyyy/MM/dd");
            // 拼接
            // 最终 filename: 2021/08/08/y6g1jo09yiw401.jpg
            filename = datePath + "/" + filename;

            // 调用oss方法实现上传
            /**
             * 参数一：Bucket名称
             * 参数二：上传到oss文件路径和文件名称 /aa/bb/1.jpg
             * 参数三：上传文件输入流
             */
            // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 返回上传后文件路径
            // 需要将上传到阿里云OSS路径手动拼接出来
            // http://ym-edu1010.oss-cn-beijing.aliyuncs.com/37.jpg
            return "https://" + bucketName + "." + endpoint + "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
