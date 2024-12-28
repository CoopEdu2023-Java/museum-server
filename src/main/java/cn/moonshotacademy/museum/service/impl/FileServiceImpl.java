package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.dto.UploadDto;
import cn.moonshotacademy.museum.entity.FileEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.FileRepository;
import cn.moonshotacademy.museum.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Autowired private FileRepository fileRepository;

    @Value("${file.storage.disk}")
    private String disk;

    @Value("${file.storage.location}")
    private String location;

    public int upload(UploadDto uploadDto) {

        MultipartFile file = uploadDto.getFile();

        String name = System.currentTimeMillis() + file.getOriginalFilename();
        Path root = Paths.get(disk + location).toAbsolutePath().normalize();
        Path url;
        try {
            url = root.resolve(Paths.get(name));
            Files.copy(file.getInputStream(), url);
        } catch (IOException e) {
            throw new BusinessException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }

        FileEntity fileEntity = new FileEntity(name, url.toString(), file.getContentType());
        fileRepository.save(fileEntity);

        return fileEntity.getId(); // 保存到数据库之后，返回自增的id
    }
}
