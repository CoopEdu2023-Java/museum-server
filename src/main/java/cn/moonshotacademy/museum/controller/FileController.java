package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.MultipleFilesDto;
import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/batch")
    public ResponseDto<List<Integer>> uploadMultipleFiles(@ModelAttribute MultipleFilesDto files) throws IOException {
        if(files.getFiles().size() == 1 && files.getFiles() == null) throw new BusinessException(ExceptionEnum.NULL_FILELIST);
        for(MultipartFile file: files.getFiles()) {
            if (file.getOriginalFilename().isBlank()) {
                throw new BusinessException(ExceptionEnum.NULL_FILENAME);
            }
        }
        List<Integer> result = fileService.uploadMultipleFiles(files);
        return ResponseDto.success(result);
    }
}
