package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtifactService {
    Page<ArtifactEntity> getFileList(Pageable pageable);
}
