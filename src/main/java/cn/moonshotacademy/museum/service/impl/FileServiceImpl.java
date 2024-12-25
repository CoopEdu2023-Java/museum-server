package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.entity.FileEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.FileRepository;
import cn.moonshotacademy.museum.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

public class FileServiceImpl implements FileService {
    @Autowired FileRepository fileRepository;

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
}
