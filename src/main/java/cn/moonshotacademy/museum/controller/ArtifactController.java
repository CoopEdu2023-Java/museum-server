package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.service.ArtifactService;
import cn.moonshotacademy.museum.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import cn.moonshotacademy.museum.dto.RequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artifacts")
public class ArtifactController {

    @Autowired
    private ArtifactService artifactService;

    @PutMapping("/{artifactId}/update")
    public ResponseDto<?> updateArtifactAndUser(
            @PathVariable Integer artifactId,
            @RequestBody RequestDto RequestDto) {
            ArtifactEntity updatedArtifact = artifactService.updateArtifactAndUser(artifactId, RequestDto);

            return ResponseDto.success("Artifact updated successfully. Artifact ID: " +
                    updatedArtifact.getId());
    }
}
