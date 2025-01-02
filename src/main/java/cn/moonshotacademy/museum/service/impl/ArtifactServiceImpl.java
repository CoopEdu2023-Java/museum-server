package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import cn.moonshotacademy.museum.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtifactServiceImpl implements ArtifactService {
    @Autowired private ArtifactRepository artifactRepository;

    public void deleteArtifact(Integer artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        artifact.setIsDeleted(true);
        artifactRepository.save(artifact);
    }

    public void restoreArtifact(Integer artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        if (artifact.getIsDeleted()) {
            artifact.setIsDeleted(false);
        }
        artifactRepository.save(artifact);
    }
}
