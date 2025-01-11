package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.config.FileProperties;
import cn.moonshotacademy.museum.dto.ArtifactDto;
import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.dto.UpdateDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.entity.FileEntity;
import cn.moonshotacademy.museum.entity.UserEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import cn.moonshotacademy.museum.repository.UserRepository;
import cn.moonshotacademy.museum.service.ArtifactService;
import cn.moonshotacademy.museum.service.ImageService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArtifactServiceImpl implements ArtifactService {
    private final ArtifactRepository artifactRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final FileProperties fileProperties;
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("jpg", "jpeg", "png");

    @Override
    public ArtifactEntity getArtifactById(int id) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(id) // AI说这里返回的是Optional<ArtifactEntity>，需要orElseThrow
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        System.out.println(artifact.getFiles());
        for (FileEntity file : artifact.getFiles()) {
            if (file.isDeleted()) {
                artifact.getFiles().remove(file);
            }
        }
        System.out.println(artifact.getFiles());
        return artifact;
    }

    @Override
    public int createEmptyArtifact() {
        ArtifactEntity emptyArtifact = new ArtifactEntity();
        emptyArtifact = artifactRepository.save(emptyArtifact);
        return emptyArtifact.getId();
    }

    @Override
    public Page<ArtifactEntity> getArtifactList(Pageable pageable) {
        return artifactRepository.findArtifactsByUsername(pageable);
    }

    @Override
    public Page<ArtifactEntity> searchFiles(String keyword, Pageable pageable) {
        return artifactRepository.searchByKeyword(keyword, pageable);
    }

    @Override
    public void uploadArtifactAvatar(AvatarDto requestData, int artifactId) throws IOException {
        MultipartFile image = requestData.getImage();
        String originalFilename = getNewFileName(image);
        validateFileType(image);

        ArtifactEntity targetEntity =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));

        String filePath =
                fileProperties.getArtifactAvatarLocation() + File.separator + originalFilename;
        Path destinationPath = Paths.get(filePath);

        ensureDirectoryExists(destinationPath.getParent().toFile());

        Files.write(destinationPath, image.getBytes());

        filePath = filePath.replace(" ", "_");
        String fileUrl = imageService.createThumbnailedImage(filePath, 1000, 1000, false);

        targetEntity.setAvatarUrl(filePath);
        targetEntity.setAvatarUrlThumb(fileUrl);
        artifactRepository.save(targetEntity);
    }

    public void deleteArtifactAvatar(int artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        artifact.setAvatarUrl(null);
        artifact.setAvatarUrlThumb(null);
        artifactRepository.save(artifact);
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

    // Generate a new file name using timestamp to avoid name conflicts
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
        return baseName + "_" + timestamp + extension; // New file name with timestamp
    }

    // Ensure the directory exists before file upload
    private void ensureDirectoryExists(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new BusinessException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }

    @Override
    public void deleteArtifact(Integer artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        if (artifact.isDeleted()) {
            throw new BusinessException(ExceptionEnum.ARTIFACT_ALREADY_DELETED);
        }
        artifact.setDeleted(true);
        artifactRepository.save(artifact);
    }

    @Override
    public ArtifactEntity updateArtifactAndUser(Integer artifactId, UpdateDto dto) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));

        artifact.setTitle(dto.getTitle());
        artifact.setIntro(dto.getIntro());
        artifact.setCompetency(dto.getCompetency());

        Set<UserEntity> currentUsers = artifact.getUserList();
        Set<Integer> newUserIds = dto.getUserIds();
        currentUsers.removeIf(user -> !newUserIds.contains(user.getId()));
        for (Integer userId : dto.getUserIds()) {
            UserEntity user =
                    userRepository
                            .findById(userId)
                            .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));

            currentUsers.add(user);

            userRepository.save(user);
        }

        return artifactRepository.save(artifact);
    }

    @Override
    public int uploadArtifact(ArtifactDto artifactDto, int artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        artifact.setTitle(artifactDto.getTitle());
        artifact.setIntro(artifactDto.getIntro());
        artifact.setCompetency(artifactDto.getCompetency());

        for (Integer userId : artifactDto.getUserIds()) {
            UserEntity user =
                    userRepository
                            .findById(userId)
                            .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));

            artifact.getUserList().add(user);
        }

        artifactRepository.save(artifact);

        return artifactId;
    }
}
