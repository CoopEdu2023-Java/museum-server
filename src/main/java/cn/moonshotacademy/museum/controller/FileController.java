package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.MultipleFilesDto;
import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.dto.UploadDto;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.service.FileService;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/batch")
    public ResponseDto<List<Integer>> uploadMultipleFiles(@ModelAttribute MultipleFilesDto files)
            throws IOException {
        if (files.getFiles().size() == 1 && files.getFiles() == null)
            throw new BusinessException(ExceptionEnum.NULL_FILELIST);
        for (MultipartFile file : files.getFiles()) {
            if (file.getOriginalFilename().isBlank()) {
                throw new BusinessException(ExceptionEnum.NULL_FILENAME);
            }
        }
        List<Integer> result = fileService.uploadMultipleFiles(files);
        return ResponseDto.success(result);
    }

    @DeleteMapping("/{file-id}")
    public ResponseDto<Void> deleteFile(@PathVariable("file-id") int fileId) {
        fileService.deleteFile(fileId);
        return ResponseDto.success();
    }

    @PostMapping("/upload")
    public ResponseDto<Integer> uploadFile(UploadDto uploadDto) throws IOException {
        if (uploadDto.getFile().isEmpty()) {
            throw new BusinessException(ExceptionEnum.EMPTY_FILE);
        }
        return ResponseDto.success(fileService.upload(uploadDto));
    }
}
