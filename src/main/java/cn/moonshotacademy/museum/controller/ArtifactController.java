package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.ArtifactDto;
import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.repository.UserRepository;
import cn.moonshotacademy.museum.service.ArtifactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArtifactController {

    private final ArtifactService artifactService;
    private final UserRepository userRepository;

    @PostMapping("/artifact/{artifactId}/upload")
    public ResponseDto<Integer> createArtifact(
            @RequestBody ArtifactDto request, @PathVariable("artifactId") int artifactId) {
        validateArtifactDto(request);
        int savedArtifactId = artifactService.uploadArtifact(request, artifactId);
        return ResponseDto.success(savedArtifactId);
    }

    public void validateArtifactDto(ArtifactDto artifactDto) {
        if (artifactDto.getTitle() == null || artifactDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (artifactDto.getAvatarUrl() == null || artifactDto.getAvatarUrl().isEmpty()) {
            throw new IllegalArgumentException("Avatar URL cannot be null or empty");
        }
        if (artifactDto.getIntro() == null || artifactDto.getIntro().isEmpty()) {
            throw new IllegalArgumentException("Intro cannot be null or empty");
        }
        if (artifactDto.getCompetency() == null || artifactDto.getCompetency().isEmpty()) {
            throw new IllegalArgumentException("Competency cannot be null or empty");
        }
        if (artifactDto.getFileIds() == null || artifactDto.getFileIds().isEmpty()) {
            throw new IllegalArgumentException("File IDs cannot be null or empty");
        }
        if (artifactDto.getUserIds() == null || artifactDto.getUserIds().isEmpty()) {
            throw new IllegalArgumentException("User IDs cannot be null or empty");
        }
    }
}
