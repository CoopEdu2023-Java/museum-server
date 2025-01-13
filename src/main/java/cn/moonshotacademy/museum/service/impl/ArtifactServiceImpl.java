package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.dto.ArtifactDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.entity.FileEntity;
import cn.moonshotacademy.museum.entity.UserEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import cn.moonshotacademy.museum.repository.FileRepository;
import cn.moonshotacademy.museum.repository.UserRepository;
import cn.moonshotacademy.museum.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtifactServiceImpl implements ArtifactService {

    @Autowired private ArtifactRepository artifactRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private FileRepository fileRepository;

    @Override
    public int uploadArtifact(ArtifactDto artifactDto, int artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        artifact.setTitle(artifactDto.getTitle());
        artifact.setAvatarUrl(artifactDto.getAvatarUrl());
        artifact.setIntro(artifactDto.getIntro());
        artifact.setCompetency(artifactDto.getCompetency());

        for (Integer userId : artifactDto.getUserIds()) {
            UserEntity user =
                    userRepository
                            .findById(userId)
                            .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));

            artifact.getUsers().add(user);
        }

        for (Integer fileId : artifactDto.getFileIds()) {
            FileEntity file =
                    fileRepository
                            .findById(fileId)
                            .orElseThrow(() -> new BusinessException(ExceptionEnum.FILE_NOT_FOUND));
            file.setArtifact(artifact);
            artifact.getFiles().add(file);
        }

        artifactRepository.save(artifact);

        return artifactId;
    }
}
