package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired private FileService fileService;

    @PostMapping("/upload")
    public ResponseDto<Integer> uploadFile(@RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(ExceptionEnum.EMPTY_FILE);
        }
        return ResponseDto.success(fileService.upload(file));
    }
}
