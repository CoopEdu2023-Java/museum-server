package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.ArtifactDto;

public interface ArtifactService {
    int uploadArtifact(ArtifactDto artifactDto, int artifactId);
}
