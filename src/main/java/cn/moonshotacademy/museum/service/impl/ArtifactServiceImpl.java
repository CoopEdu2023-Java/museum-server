package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import cn.moonshotacademy.museum.service.ArtifactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtifactServiceImpl implements ArtifactService {
    private final ArtifactRepository artifactRepository;

    @Override
    public Page<ArtifactEntity> getArtifactList(Pageable pageable) {
        return artifactRepository.findArtifactsByUsername(pageable);
    }

    @Override
    public Page<ArtifactEntity> searchFiles(String keyword, Pageable pageable) {
        return artifactRepository.searchByKeyword(keyword, pageable);
    }
}
