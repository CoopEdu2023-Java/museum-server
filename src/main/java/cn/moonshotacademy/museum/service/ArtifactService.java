package cn.moonshotacademy.museum.service;

import org.springframework.stereotype.Service;

@Service
public interface ArtifactService {
    void deleteArtifact(Integer artifactId);

    void restoreArtifact(Integer artifactId);
}
