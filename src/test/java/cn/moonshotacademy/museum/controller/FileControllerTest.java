package cn.moonshotacademy.museum.controller;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.moonshotacademy.museum.config.SecurityConfig;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.service.FileService;
import cn.moonshotacademy.museum.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FileController.class)
@WithMockUser
@Import({SecurityConfig.class, JwtServiceImpl.class})
public class FileControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private FileService fileService;

    @Test
    public void deleteFile_Success() throws Exception {
        int fileId = 1;
        willDoNothing().given(fileService).deleteFile(fileId);
        mockMvc
                .perform(delete("/files/" + fileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    public void deleteFile_NotFound() throws Exception {
        int fileId = 1;
        willThrow(new BusinessException(ExceptionEnum.FILE_NOT_FOUND))
                .given(fileService)
                .deleteFile(fileId);
        mockMvc
                .perform(delete("/files/" + fileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ExceptionEnum.FILE_NOT_FOUND.getCode()));
    }
}
