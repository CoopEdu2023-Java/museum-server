package cn.moonshotacademy.museum.dto;

import lombok.Data;

@Data
public class DeleteArtifactDto {
    public Boolean isDeleted;
    private Integer id;
}
