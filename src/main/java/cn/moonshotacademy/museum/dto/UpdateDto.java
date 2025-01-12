package cn.moonshotacademy.museum.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateDto {
    private String title;
    private String intro;
    private String competency;
    private String category;
    private String type;
    private Set<Integer> userIds;

    private String email;
    private String role;
    private String defaultName;
    private String englishName;
    private String userIntro;
}
