package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.MultipleFilesDto;

import java.io.IOException;
import java.util.List;

public interface FileService {
    public List<Integer> uploadMultipleFiles(MultipleFilesDto files) throws IOException;
}
