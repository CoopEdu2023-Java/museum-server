package cn.moonshotacademy.museum.controller;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/")
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

    @GetMapping("/create")
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
}
