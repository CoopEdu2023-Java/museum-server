package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.DeleteArtifactDto;
import org.springframework.stereotype.Service;

@Service
public interface ArtifactService {
    void deleteArtifact(DeleteArtifactDto deleteArtifactDto);

    void restoreArtifact(DeleteArtifactDto deleteArtifactDto);
}
