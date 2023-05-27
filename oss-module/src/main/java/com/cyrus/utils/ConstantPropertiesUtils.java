package com.cyrus.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {
    //定义公开静态常量
    public static String END_POINT;
    public static String OSS_ID;
    public static String OSS_SECRET;
    public static String OSS_BUCKETNAME;
    //读取配置文件内容
    //使用value注解，添加表达式
    @Value("${aliyun.oss.endpoint}")
    private final String endPoint = "oss-cn-beijing.aliyuncs.com";
    @Value("${aliyun.oss.accessKeyId}")
    private final String oss_id = "LTAI5tL2AzD9j1VfMkZ1rFYt";
    @Value("${aliyun.oss.accessKeySecret}")
    private final String oss_secret = "Yzc7x3g1eArAsWkUDURD0231VTvMqz";
    @Value("${aliyun.oss.bucketName}")
    private final String oss_bucketName = "edu-systembase";

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endPoint;
        OSS_ID = oss_id;
        OSS_SECRET = oss_secret;
        OSS_BUCKETNAME = oss_bucketName;
    }
}
