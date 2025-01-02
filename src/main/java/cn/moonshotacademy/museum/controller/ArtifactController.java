package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;

import cn.moonshotacademy.museum.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ArtifactController {

    @Autowired
    private ArtifactService artifactService;

    @GetMapping("/artifacts/")
    public ResponseDto<Void> handleMissingId() {
        throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
    }

    @GetMapping("/artifacts/{id}")
    public ResponseDto<ArtifactEntity> getArtifactById(@PathVariable String id) {
        int artifactId;
        
        try {
            artifactId = Integer.parseInt(id);

        } catch (NumberFormatException e) {
            throw new BusinessException(ExceptionEnum.ILLEGAL_PARAMETERS);
        }

        ArtifactEntity data = artifactService.getArtifactById(artifactId);

        return new ResponseDto<ArtifactEntity>(data);
    }
    @GetMapping("/artifacts/create")
    public ResponseDto<Integer> createNewArtifact() {
        Integer data = artifactService.createEmptyArtifact();
        return new ResponseDto<Integer>(data);
    }
}
