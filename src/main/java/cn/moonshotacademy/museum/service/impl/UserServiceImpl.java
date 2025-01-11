package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.config.FileProperties;
import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.dto.LoginRequestDto;
import cn.moonshotacademy.museum.entity.UserEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.UserRepository;
import cn.moonshotacademy.museum.service.ImageService;
import cn.moonshotacademy.museum.service.JwtService;
import cn.moonshotacademy.museum.service.UserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileProperties fileProperties;
    private final ImageService imageService;
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("jpg", "jpeg", "png");
    private final JwtService jwtService;

    public UserServiceImpl(
            FileProperties fileProperties, UserRepository userRepository, ImageService imageService, JwtService jwtService) {
        this.fileProperties = fileProperties;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.jwtService = jwtService;
    }

    @Override
    public void uploadUserAvatar(AvatarDto requestData, int userId) throws IOException {
        MultipartFile image = requestData.getImage();
        String originalFilename = getNewFileName(image);
        validateFileType(image);

        if (originalFilename.isBlank()) {
            throw new BusinessException(ExceptionEnum.NULL_FILENAME);
        }

        UserEntity targetEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));

        String filePath = fileProperties.getUserAvatarLocation() + File.separator + originalFilename;
        Path destinationPath = Paths.get(filePath);

        ensureDirectoryExists(destinationPath.getParent().toFile());
        Files.write(destinationPath, image.getBytes());
        filePath = fileProperties.getUserAvatarUrlBase() + originalFilename;
        String fileUrl = imageService.createThumbnailedImage(filePath, 200, 200, true);
        targetEntity.setAvatarUrl(filePath);
        userRepository.save(targetEntity);
    }

    @Override
    public void deleteUserAvatar(int userId) {
        UserEntity targetEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));

        targetEntity.setAvatarUrl(null);
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

    // 生成新的文件名，避免文件名冲突
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
        String baseName = originalFilename.substring(0, dotIndex);
        return baseName + "_" + timestamp + extension;
    }

    // 确保目录存在，如果目录不存在则创建
    private void ensureDirectoryExists(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new BusinessException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }

    public UserEntity findLearner(String name) {
        UserEntity user =
                (userRepository.findByDefaultName(name) == null)
                        ? userRepository
                                .findByEnglishName(name)
                                .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND))
                        : userRepository
                                .findByDefaultName(name)
                                .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));
        if (user == null) {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }
        if (user.getType().equals("learner")) {
            return user;
        } else {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }
    }

    public UserEntity findInstructor(String name) {
        UserEntity user =
                (userRepository.findByDefaultName(name) == null)
                        ? userRepository
                                .findByEnglishName(name)
                                .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND))
                        : userRepository
                                .findByDefaultName(name)
                                .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));
        if (user == null) {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }
        if (user.getType().equals("instructor")) {
            return user;
        } else {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }
    }

    public String login(LoginRequestDto loginRequestDto) {
        UserEntity userFromUserName;
        if (userRepository.findByEnglishName(loginRequestDto.getName()) == null || userRepository.findByDefaultName(loginRequestDto.getName()) == null) {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }else if (userRepository.findByEnglishName(loginRequestDto.getName()) == null){
            System.out.println(111111111);
            userFromUserName = userRepository.findByDefaultName(loginRequestDto.getName())
                    .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));
        }else{
            System.out.println(222222222);
            userFromUserName = userRepository.findByEnglishName(loginRequestDto.getName())
                    .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));
        }

        UserEntity userFromEmail = userRepository.findByEmail(loginRequestDto.getEmail())
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));
        System.out.println(userFromEmail);
        if(userFromEmail.equals(userFromUserName)) {
            return jwtService.setToken(userFromEmail.getId());
        } else {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }
    }
}
