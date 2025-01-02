package cn.moonshotacademy.museum.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import cn.moonshotacademy.museum.entity.FileEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.FileRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {
    @Mock FileRepository fileRepository;
    @InjectMocks FileServiceImpl fileService;

    @Test
    public void deleteFile_Success() {
        int fileId = 1;
        FileEntity fileEntity = new FileEntity("", "");
        given(fileRepository.findById(fileId)).willReturn(Optional.of(fileEntity));

        fileService.deleteFile(fileId);

        FileEntity deletedFileEntity = new FileEntity("", "");
        deletedFileEntity.setDeleted(true);
        verify(fileRepository).save(deletedFileEntity);
    }

    @Test
    public void deleteFile_FileNotFound() {
        int fileId = 1;
        given(fileRepository.findById(fileId)).willReturn(Optional.empty());

        BusinessException exception =
                assertThrows(BusinessException.class, () -> fileService.deleteFile(fileId));

        assertEquals(exception.getCode(), ExceptionEnum.FILE_NOT_FOUND.getCode());
    }

    @Test
    public void deleteFile_FileAlreadyDeleted() {
        int fileId = 1;
        FileEntity fileEntity = new FileEntity("", "");
        fileEntity.setDeleted(true);
        given(fileRepository.findById(fileId)).willReturn(Optional.of(fileEntity));

        BusinessException exception =
                assertThrows(BusinessException.class, () -> fileService.deleteFile(fileId));

        assertEquals(exception.getCode(), ExceptionEnum.FILE_NOT_FOUND.getCode());
    }
}
