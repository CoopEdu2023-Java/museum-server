package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.ArtifactDto;
import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.dto.UpdateDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ArtifactService {
    ArtifactEntity getArtifactById(int id);

    int createEmptyArtifact();

    void deleteArtifact(Integer artifactId);

    ArtifactEntity updateArtifactAndUser(Integer artifactId, UpdateDto dto);

    int uploadArtifact(ArtifactDto artifactDto, int artifactId);

    Page<ArtifactEntity> getArtifactList(Pageable pageable);

    Page<ArtifactEntity> searchFiles(String keyword, Pageable pageable);

    public void uploadArtifactAvatar(AvatarDto requestData, int artifactId) throws IOException;
}
