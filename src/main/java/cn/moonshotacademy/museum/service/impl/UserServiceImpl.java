package cn.moonshotacademy.museum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.moonshotacademy.museum.config.FileProperties;
import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.entity.UserEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.UserRepository;
import cn.moonshotacademy.museum.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileProperties fileProperties;
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("jpg", "jpeg", "png");

    @Autowired
    public UserServiceImpl(FileProperties fileProperties, UserRepository userRepository) {
        this.fileProperties = fileProperties;
        this.userRepository = userRepository;
    }

    @Override
    public void uploadUserAvatar(AvatarDto requestData, int userId) throws IOException {
        MultipartFile image = requestData.getImage();
        String originalFilename = getNewFileName(image);
        validateFileType(image);
        if (originalFilename.isBlank()) {
            throw new BusinessException(ExceptionEnum.NULL_FILENAME);
        }
        UserEntity targetEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));

        String filePath = fileProperties.getUserAvatarLocation() + File.separator + originalFilename;
        File destination = new File(filePath);
        image.transferTo(destination);

        targetEntity.setAvatarUrl(filePath);
        userRepository.save(targetEntity);
    }
    private void validateFileType(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new BusinessException(ExceptionEnum.NULL_FILENAME);
        }

        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!ALLOWED_FILE_TYPES.contains(extension)) {
            throw new BusinessException(ExceptionEnum.TYPE_NOTALLOW);
        }
    }
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex > 0) ? filename.substring(dotIndex + 1) : "";
    }
    private static String getNewFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new BusinessException(ExceptionEnum.NULL_FILENAME);
        }

        String extension = "";
        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        String baseName = dotIndex > 0 ? originalFilename.substring(0, dotIndex) : originalFilename;
        return baseName + "_" + timestamp + extension;
    }
}
