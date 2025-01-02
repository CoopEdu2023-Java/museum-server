package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import cn.moonshotacademy.museum.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artifacts")
public class ArtifactController {

    @Autowired ArtifactService artifactService;
    @Autowired private ArtifactRepository artifactRepository;

    @DeleteMapping("/{artifactId}")
    public ResponseDto<Void> deleteArtifact(@PathVariable Integer artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        if (artifact.getIsDeleted()) {
            throw new BusinessException(ExceptionEnum.ALREADY_DELETED);
        }
        artifactService.deleteArtifact(artifactId);
        return ResponseDto.success();
    }

    @PatchMapping("/{artifactId}/restore")
    public ResponseDto<Void> restoreArtifact(@PathVariable Integer artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        if (!artifact.getIsDeleted()) {
            throw new BusinessException(ExceptionEnum.IS_NOT_DELETED);
        }
        artifactService.restoreArtifact(artifactId);
        return ResponseDto.success();
    }
}
