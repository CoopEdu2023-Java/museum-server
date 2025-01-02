package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.config.FileProperties;
import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import cn.moonshotacademy.museum.service.ArtifactService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArtifactServiceImpl implements ArtifactService {
    private final ArtifactRepository artifactRepository;
    private FileProperties fileProperties;
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("jpg", "jpeg", "png");
    
        @Override
        public ArtifactEntity getArtifactById(int id) {
            ArtifactEntity artifact = artifactRepository.findById(id) //AI说这里返回的是Optional<ArtifactEntity>，需要orElseThrow
                    .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
            System.out.println(artifact.getFiles());
            return artifact;
        }
    
        @Override
        public int createEmptyArtifact(){
            ArtifactEntity emptyArtifact = new ArtifactEntity();
            emptyArtifact = artifactRepository.save(emptyArtifact);
            return emptyArtifact.getId();
        }
    
        @Override
        public String createThumbnailedPicture(String inputFilePath) {
            String outputFilePath = "Thumbnailed" + "_" + inputFilePath;
    
            return outputFilePath;
        }
    
    
        @Override
        public Page<ArtifactEntity> getArtifactList(Pageable pageable) {
            return artifactRepository.findArtifactsByUsername(pageable);
        }
    
        @Override
        public Page<ArtifactEntity> searchFiles(String keyword, Pageable pageable) {
            return artifactRepository.searchByKeyword(keyword, pageable);
        }
        @Autowired
        public ArtifactServiceImpl(FileProperties fileProperties, ArtifactRepository artifactRepository) {
            this.fileProperties = fileProperties;
        this.artifactRepository = artifactRepository;
    }

    @Override
    public void uploadArtifactAvatar(AvatarDto requestData, int artifactId) throws IOException {
        MultipartFile image = requestData.getImage();
        String originalFilename = getNewFileName(image);
        validateFileType(image);
        
        // Retrieve the artifact entity
        ArtifactEntity targetEntity = artifactRepository.findById(artifactId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));

        // Define file storage path
        String filePath = fileProperties.getArtifactAvatarLocation() + File.separator + originalFilename;
        Path destinationPath = Paths.get(filePath);
        
        // Ensure the directory exists before saving the file
        ensureDirectoryExists(destinationPath.getParent().toFile());
        
        // Save the file using Files.write()
        Files.write(destinationPath, image.getBytes());

        // Construct file URL (assuming it's accessible publicly)
        String fileUrl = fileProperties.getArtifactAvatarUrlBase() + originalFilename;

        // Update the entity with the file URL
        targetEntity.setAvatarUrl(fileUrl);
        artifactRepository.save(targetEntity);
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
        return baseName + "_" + timestamp + extension;  // New file name with timestamp
    }

    // Ensure the directory exists before file upload
    private void ensureDirectoryExists(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new BusinessException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }
}