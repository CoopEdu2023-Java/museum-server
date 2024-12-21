package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.service.ArtifactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/")
public class ArtifactController {
    private final ArtifactService artifactService;

    @Autowired
    public ArtifactController(ArtifactService ArtifactService) {
        this.artifactService = ArtifactService;
    }

    @GetMapping("/Artifacts")
    public ResponseDto<Page<ArtifactEntity>> getFileList(
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "10") String size,
            @RequestParam String search) {
        try {
            int pageNumber = Integer.parseInt(page);
            int pageSize = Integer.parseInt(size);

            if (pageNumber < 0 || pageSize <= 0) {
                log.warn("Invalid pagination parameters: page={}, size={}", page, size);
                throw new BusinessException(ExceptionEnum.INVALID_ENTRY);
            }

            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return new ResponseDto<>(0, "", artifactService.getArtifactList(pageable));
        } catch (NumberFormatException e) {
            log.warn("Pagination parameters are not numbers: page={}, size={}", page, size);
            throw new BusinessException(ExceptionEnum.INVALID_ENTRY_TYPE);
        }

    }
}
