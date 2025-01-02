package cn.moonshotacademy.museum.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileProperties {
    private String storageLocation;        
    private String userAvatarLocation;    
    private String artifactAvatarLocation; 

    private String storageUrlBase;         // 文件存储路径的 URL 前缀
    private String userAvatarUrlBase;      // 用户头像的访问 URL 前缀
    private String artifactAvatarUrlBase;  // 文物头像的访问 URL 前缀
}
