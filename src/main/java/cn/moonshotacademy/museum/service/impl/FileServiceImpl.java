package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.config.OSSConfig;
import cn.moonshotacademy.museum.entity.FileEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.FileRepository;
import cn.moonshotacademy.museum.service.FileService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Autowired private FileRepository fileRepository;

    @Autowired private OSSConfig ossConfig;

    public int upload(MultipartFile file) {

        String name = UUID.randomUUID().toString() + file.getOriginalFilename();
        String endpoint = ossConfig.getEndpoint(); // 域名
        String bucketName = ossConfig.getBucketName(); // 仓库名
        String url;
        try {
            InputStream inputStream = file.getInputStream();
            OSS ossClient =
                    new OSSClientBuilder()
                            .build(endpoint, ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
            ossClient.putObject(bucketName, name, inputStream);

            url =
                    ossClient
                            .generatePresignedUrl(
                                    bucketName, name, new Date(System.currentTimeMillis() + 3600 * 1000))
                            .toString();
            ossClient.shutdown();

        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }

        FileEntity fileEntity = new FileEntity(name, url, file.getContentType());
        fileRepository.save(fileEntity);

        return fileEntity.getId(); // 保存到数据库之后，返回自增的id
    }
}
