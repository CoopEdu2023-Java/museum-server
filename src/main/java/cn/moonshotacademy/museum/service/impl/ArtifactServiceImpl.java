package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.dto.DeleteArtifactDto;
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

    public void deleteArtifact(DeleteArtifactDto deleteArtifactDto) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById((deleteArtifactDto.getId()))
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        artifact.setIs_deleted(true);
        artifactRepository.save(artifact);
    }

    public void restoreArtifact(DeleteArtifactDto deleteArtifactDto) {
        ArtifactEntity artifact =
                artifactRepository.findById((deleteArtifactDto.getId())).orElseThrow();
        if (deleteArtifactDto.getIs_deleted()) {
            deleteArtifactDto.setIs_deleted(false);
        }
        artifactRepository.save(artifact);
    }
}
