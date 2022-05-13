package com.superb.upetstar.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author orchid
 * @date 2021-08-08 18:06
 * @description
 */
// 当项目启动, spring接口, spring加载后, 执行接口一个方法
// 在方法中进行处理. private的值虽说能读取, 但是后面用不了
@RefreshScope
@Configuration
public class ConstantPropertiesUtils implements InitializingBean {

    // 当项目启动spring创建实例并交由spring管理，会将文件中的内容读取出来，赋值给endPoint
    // 读取配置文件内容
    @Value("${aliyun.oss.file.endpoint}") // 属性注入注解
    private String endPoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    // 定义公开静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;


    /**
     * 上述属性初始化后，执行该方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endPoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
