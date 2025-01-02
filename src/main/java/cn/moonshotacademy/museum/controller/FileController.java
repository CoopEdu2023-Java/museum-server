package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired private FileService fileService;

    @DeleteMapping("/{file-id}")
    public ResponseDto<Void> deleteFile(@PathVariable("file-id") int fileId) {
        fileService.deleteFile(fileId);
        return ResponseDto.success();
    }
}
