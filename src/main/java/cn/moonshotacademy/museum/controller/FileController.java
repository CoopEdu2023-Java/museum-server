package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.dto.UploadDto;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired private FileService fileService;

    @PostMapping("/upload")
    public ResponseDto<Integer> uploadFile(UploadDto uploadDto) {
        if (uploadDto.getFile().isEmpty()) {
            throw new BusinessException(ExceptionEnum.EMPTY_FILE);
        }
        return ResponseDto.success(fileService.upload(uploadDto));
    }
}
