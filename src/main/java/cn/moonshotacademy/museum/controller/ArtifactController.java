package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.DeleteArtifactDto;
import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artifacts")
public class ArtifactController {

    @Autowired ArtifactService artifactService;

    @PatchMapping("/{artifactId}")
    public ResponseDto<Void> deleteArtifact(
            @PathVariable Integer artifactId, DeleteArtifactDto deleteArtifactDto) {
        deleteArtifactDto.setId(artifactId);
        if (deleteArtifactDto.getIs_deleted()) {
            throw new BusinessException(ExceptionEnum.ALREADY_DELETED);
        }
        artifactService.deleteArtifact(deleteArtifactDto);
        return new ResponseDto<>();
    }

    @PatchMapping("/RestoreArtifact")
    public ResponseDto<Void> restoreArtifact(@RequestBody DeleteArtifactDto deleteArtifactDto) {
        if (!deleteArtifactDto.getIs_deleted()) {
            throw new BusinessException(ExceptionEnum.ALREADY_DELETED);
        }
        artifactService.restoreArtifact(deleteArtifactDto);
        return ResponseDto.success();
    }
}
