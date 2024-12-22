package cn.moonshotacademy.museum.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class OSSConfig {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucket-name}")
    private String bucketName;

    @Value("${oss.access-key-id}")
    private String accessKeyId;

    @Value("${oss.access-key-secret}")
    private String accessKeySecret;
}
