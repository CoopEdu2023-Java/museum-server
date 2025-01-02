package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


@Service
public interface ArtifactService {
    ArtifactEntity getArtifactById(int id);
    int createEmptyArtifact();
    String createThumbnailedPicture(String inputFilePath);
    Page<ArtifactEntity> getArtifactList(Pageable pageable);

    Page<ArtifactEntity> searchFiles(String keyword, Pageable pageable);
    public void uploadArtifactAvatar(AvatarDto requestData, int artifactId) throws IOException;
}