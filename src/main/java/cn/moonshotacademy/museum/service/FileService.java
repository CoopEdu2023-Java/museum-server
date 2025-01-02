package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.MultipleFilesDto;
import cn.moonshotacademy.museum.dto.UploadDto;

import java.io.IOException;
import java.util.List;

public interface FileService {
    public List<Integer> uploadMultipleFiles(MultipleFilesDto files) throws IOException;
    public void deleteFile(int fileId);
    public int upload(UploadDto uploadDto) throws IOException;
}
