package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
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
    public ArtifactEntity getArtifactById(int id) {
        ArtifactEntity artifact = artifactRepository.findById(id) //AI说这里返回的是Optional<ArtifactEntity>，需要orElseThrow
                .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        System.out.println(artifact.getFiles());
        return artifact;
    }

    @Override
    public int createEmptyArtifact(){
        ArtifactEntity emptyArtifact = new ArtifactEntity();
        emptyArtifact = artifactRepository.save(emptyArtifact);
        return emptyArtifact.getId();
    }

    @Override
    public String createThumbnailedPicture(String inputFilePath) {
        String outputFilePath = "Thumbnailed" + "_" + inputFilePath;

        return outputFilePath;
    }


    @Override
    public Page<ArtifactEntity> getArtifactList(Pageable pageable) {
        return artifactRepository.findArtifactsByUsername(pageable);
    }

    @Override
    public Page<ArtifactEntity> searchFiles(String keyword, Pageable pageable) {
        return artifactRepository.searchByKeyword(keyword, pageable);
    }

    @Override
    public void deleteArtifact(Integer artifactId) {
        ArtifactEntity artifact =
                artifactRepository
                        .findById(artifactId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        if (artifact.isDeleted()){
            throw new BusinessException(ExceptionEnum.ARTIFACT_ALREADY_DELETED);
        }
        artifact.setDeleted(true);
        artifactRepository.save(artifact);
    }
}