package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.dto.ArtifactDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.entity.UserEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import cn.moonshotacademy.museum.repository.UserRepository;
import cn.moonshotacademy.museum.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtifactServiceImpl implements ArtifactService {

    @Autowired private ArtifactRepository artifactRepository;

    @Autowired private UserRepository userRepository;

    @Override
    public int uploadArtifact(ArtifactDto artifactDto, int artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        artifact.setTitle(artifactDto.getTitle());
        artifact.setAvatar_url(artifactDto.getAvatarUrl());
        artifact.setIntro(artifactDto.getIntro());
        artifact.setCompetency(artifactDto.getCompetency());

        for (Integer userId : artifactDto.getUserIds()) {
            UserEntity user =
                    userRepository
                            .findById(userId)
                            .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));
        }

        artifactRepository.save(artifact);
        return artifactId;
    }
}
