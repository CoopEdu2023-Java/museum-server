package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.config.FileProperties;
import cn.moonshotacademy.museum.dto.MultipleFilesDto;
import cn.moonshotacademy.museum.dto.UploadDto;
import cn.moonshotacademy.museum.entity.FileEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.FileRepository;
import cn.moonshotacademy.museum.service.FileService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    private final FileProperties fileProperties;
    private final FileRepository fileRepository;

    public FileServiceImpl(FileProperties fileProperties, FileRepository fileRepository) {
        this.fileProperties = fileProperties;
        this.fileRepository = fileRepository;
    }

    @Override
    @Transactional
    public List<Integer> uploadMultipleFiles(MultipleFilesDto RequestDto) throws IOException {
        List<Integer> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : RequestDto.getFiles()) {
            String originalFilename = getNewFileName(file);

            if (originalFilename.isBlank()) {
                throw new BusinessException(ExceptionEnum.NULL_FILENAME);
            }

            // Define the file storage path
            String filePath = fileProperties.getStorageLocation() + File.separator + originalFilename;
            File destination = new File(filePath);

            // Ensure the directory exists
            ensureDirectoryExists(destination.getParentFile());

            // Write file to the storage path
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes()); // Write the file bytes to the defined path

            // Save the file information into the database
            FileEntity uploadedFile = new FileEntity();
            uploadedFile.setName(originalFilename);
            uploadedFile.setType(Files.probeContentType(path));
            uploadedFile.setUrl(filePath);
            fileRepository.save(uploadedFile);
            uploadedFiles.add(uploadedFile.getId());
        }

        // Check if all files were uploaded successfully
        if (uploadedFiles.size() != RequestDto.getFiles().size()) {
            throw new BusinessException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }

        return uploadedFiles;
    }

    @Override
    public void deleteFile(int fileId) {
        FileEntity file =
                fileRepository
                        .findById(fileId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.FILE_NOT_FOUND));
        if (file.isDeleted()) {
            throw new BusinessException(ExceptionEnum.FILE_NOT_FOUND);
        }
        file.setDeleted(true);
        fileRepository.save(file);
    }

    @Override
    @Transactional
    public int upload(UploadDto uploadDto) throws IOException {
        MultipartFile file = uploadDto.getFile();

        // Generate a new file name
        String originalFilename = getNewFileName(file);

        if (originalFilename.isBlank()) {
            throw new BusinessException(ExceptionEnum.NULL_FILENAME);
        }

        // Define the file storage path
        String filePath = fileProperties.getStorageLocation() + File.separator + originalFilename;
        File destination = new File(filePath);

        // Ensure the directory exists
        ensureDirectoryExists(destination.getParentFile());

        // Write file to the storage path
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes()); // Write the file bytes to the defined path

        // Save the file information into the database
        FileEntity uploadedFile = new FileEntity();
        uploadedFile.setName(originalFilename);
        uploadedFile.setType(file.getContentType());
        uploadedFile.setUrl(filePath);
        fileRepository.save(uploadedFile);

        return uploadedFile.getId(); // Return the ID after saving to the database
    }

    // Ensure the directory exists, if not, create it
    private void ensureDirectoryExists(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new BusinessException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }

    // Generate a new file name with timestamp to avoid conflicts
    private static String getNewFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new BusinessException(ExceptionEnum.NULL_FILENAME);
        }

        String extension = "";
        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex); // Get the file extension
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        String baseName = originalFilename.substring(0, dotIndex);
        return baseName + "_" + timestamp + extension; // Construct the new file name
    }
}
