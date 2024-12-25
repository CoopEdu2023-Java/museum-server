package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.service.ArtifactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/artifacts")
public class ArtifactController {
    private final ArtifactService artifactService;

    public ArtifactController(ArtifactService ArtifactService) {
        this.artifactService = ArtifactService;
    }

    @GetMapping
    public ResponseDto<Page<ArtifactEntity>> getFileList(
            @RequestParam String page,
            @RequestParam String size,
            @RequestParam String search) {
        try {
            int pageNumber = Integer.parseInt(page);
            int pageSize = Integer.parseInt(size);

            if (pageNumber < 0 || pageSize <= 0) {
                log.warn("Invalid pagination parameters: page={}, size={}", page, size);
                throw new BusinessException(ExceptionEnum.INVALID_ENTRY);
            }

            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return ResponseDto.success(artifactService.getArtifactList(pageable));
        } catch (NumberFormatException e) {
            log.warn("Pagination parameters are not numbers: page={}, size={}", page, size);
            throw new BusinessException(ExceptionEnum.INVALID_ENTRY_TYPE);
        }

    }
}
