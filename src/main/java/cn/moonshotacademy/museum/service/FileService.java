package cn.moonshotacademy.museum.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public int upload(MultipartFile file);
}
