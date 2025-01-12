package cn.moonshotacademy.museum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.moonshotacademy.museum.controller.ArtifactController;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.service.ArtifactService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class ArtifactControllerTest {
    @Autowired private MockMvc mockMvc;

    @Mock private ArtifactService artifactService;

    @InjectMocks private ArtifactController artifactController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(artifactController).build();
    }

    @Test
    public void testGetArtifactById_ValidId() throws Exception {
        ArtifactEntity artifact = new ArtifactEntity();
        artifact.setId(1);
        artifact.setTitle("Artifact Title");

        when(artifactService.getArtifactById(1)).thenReturn(artifact);

        mockMvc
                .perform(get("/artifact/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Artifact Title"));
    }

    @Test
    public void testGetArtifactById_InvalidId() throws Exception {

        String param = "/abc";
        Exception exception =
                assertThrows(
                        ServletException.class,
                        () -> {
                            mockMvc
                                    .perform(MockMvcRequestBuilders.get("/artifact" + param))
                                    .andDo(MockMvcResultHandlers.print());
                        });

        Throwable rootCause = exception.getCause();
        assertTrue(rootCause instanceof BusinessException);
        assertEquals("Illegal Parameters", rootCause.getMessage());
    }

    @Test
    public void testHandleMissingId() throws Exception {
        String param = "/";
        Exception exception =
                assertThrows(
                        ServletException.class,
                        () -> {
                            mockMvc
                                    .perform(MockMvcRequestBuilders.get("/artifact" + param))
                                    .andDo(MockMvcResultHandlers.print());
                        });

        Throwable rootCause = exception.getCause();
        assertTrue(rootCause instanceof BusinessException);
        assertEquals("Missing Parameters", rootCause.getMessage());
    }
}
