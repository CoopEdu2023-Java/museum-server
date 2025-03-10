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
    private Set<Integer> userIds;
}
