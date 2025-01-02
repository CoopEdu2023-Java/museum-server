package cn.moonshotacademy.museum.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.service.ArtifactService;

@RestController
@RequestMapping("/artifacts")
public class ArtifactController {
    private final ArtifactService artifactService;

    @Autowired
    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    @PostMapping("/{artifactId}/avatars/add")
    public ResponseDto<Void> uploadArtifactAvatar(@PathVariable int artifactId, @ModelAttribute AvatarDto image) throws IOException {
            artifactService.uploadArtifactAvatar(image, artifactId);
            return ResponseDto.success();
    }
}
