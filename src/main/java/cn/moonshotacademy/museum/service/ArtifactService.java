package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.AvatarDto;

import java.io.IOException;

public interface ArtifactService {
    public void uploadArtifactAvatar(AvatarDto requestData, int artifactId) throws IOException;
}
