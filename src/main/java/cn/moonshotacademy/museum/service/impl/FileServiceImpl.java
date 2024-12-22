package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.dto.FileDto;
import cn.moonshotacademy.museum.entity.FileEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.FileRepository;
import cn.moonshotacademy.museum.service.FileService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
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

    public FileDto upload(FileDto fileDto) {

        MultipartFile file = fileDto.getFile();

        String name = UUID.randomUUID().toString() + file.getOriginalFilename();
        Path root = Paths.get(disk + location).toAbsolutePath().normalize();
        Path url;
        try {
            url = root.resolve(Paths.get(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), url);
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }

        FileEntity fileEntity = new FileEntity(name, url.toString(), file.getContentType());
        fileRepository.save(fileEntity);

        return new FileDto(null, fileEntity.getId()); // 保存到数据库之后，返回自增的id
    }
}
