package com.chx.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "oss")
@Component
@Data
//动态获取nacos配置里面的值
//@RefreshScope
public class OssConfig {
    //ak
    private String accessKey;
    //sk
    private String secretKey;
    //空间名称
    private String bucket;
    //外链
    private String domainname;
}
