package cn.moonshotacademy.museum.dto;

import lombok.Data;

@Data
public class DeleteArtifactDto {
    public Boolean is_deleted;
    private Integer id;
}
