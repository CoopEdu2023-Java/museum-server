package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import cn.moonshotacademy.museum.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArtifactServiceImpl implements ArtifactService {
    private final ArtifactRepository artifactRepository;

    @Autowired
    public ArtifactServiceImpl(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    @Override
    public Page<ArtifactEntity> getArtifactList(Pageable pageable) {
        return artifactRepository.findArtifactsByUsername(pageable);
    }
}
