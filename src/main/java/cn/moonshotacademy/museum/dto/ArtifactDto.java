package cn.moonshotacademy.museum.dto;

import java.util.List;
import lombok.Data;

@Data
public class ArtifactDto {
    private String title;
    private String intro;
    private String competency;
    private List<Integer> userIds;
}
