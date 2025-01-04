package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.ArtifactDto;
import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;

import cn.moonshotacademy.museum.service.ArtifactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/artifacts")
@RequiredArgsConstructor
public class ArtifactController {

    @Autowired
    private ArtifactService artifactService;

    @GetMapping("")
    public ResponseDto<Page<ArtifactEntity>> getFileList(
            @RequestParam String page, @RequestParam String size, @RequestParam String search) {
        try {
            int pageNumber = Integer.parseInt(page);
            int pageSize = Integer.parseInt(size);

            if (pageNumber < 0 || pageSize <= 0) {
                log.warn("Invalid pagination parameters: page={}, size={}", page, size);
                throw new BusinessException(ExceptionEnum.INVALID_ENTRY);
            }
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            if (search.isEmpty()) {
                return ResponseDto.success(artifactService.getArtifactList(pageable));
            } else {
                return new ResponseDto<>(0, "", artifactService.searchFiles(search, pageable));
            }
        } catch (NumberFormatException e) {
            log.warn("Pagination parameters are not numbers: page={}, size={}", page, size);
            throw new BusinessException(ExceptionEnum.INVALID_ENTRY_TYPE);
        }
    }

    @GetMapping("/{id}/get")
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

    @PostMapping("/create")
    public ResponseDto<Integer> createNewArtifact() {
        Integer data = artifactService.createEmptyArtifact();
        return new ResponseDto<Integer>(data);
    }

    @PatchMapping("/{artifactId}")
    public ResponseDto<Void> deleteArtifact(@PathVariable Integer artifactId) {
        artifactService.deleteArtifact(artifactId);
        return ResponseDto.success();
    }

    @PostMapping("/{artifactId}/avatars/add")
    public ResponseDto<Void> uploadArtifactAvatar(@PathVariable int artifactId, @ModelAttribute AvatarDto image) throws IOException {
            artifactService.uploadArtifactAvatar(image, artifactId);
            return ResponseDto.success();
    }

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
