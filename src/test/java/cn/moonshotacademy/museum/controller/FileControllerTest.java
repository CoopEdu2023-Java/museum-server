package cn.moonshotacademy.museum.controller;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.moonshotacademy.museum.config.SecurityConfig;
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
        int fileId = 0;
        willDoNothing().given(fileService).deleteFile(fileId);
        mockMvc.perform(delete("/files/" + fileId)).andExpect(status().isOk());
    }
}
