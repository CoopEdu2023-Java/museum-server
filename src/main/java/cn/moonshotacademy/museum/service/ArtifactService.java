package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.dto.RequestDto;

public interface ArtifactService {
    ArtifactEntity updateArtifactAndUser(Integer artifactId, RequestDto dto);
}
