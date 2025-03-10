package cn.moonshotacademy.museum.dto;

import cn.moonshotacademy.museum.entity.CompetencyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetencyDto {
    private int id;
    private String name;
    public CompetencyDto(CompetencyEntity competencyEntity) {

        this.id = competencyEntity.getId();
        this.name = competencyEntity.getName();
    }
}
