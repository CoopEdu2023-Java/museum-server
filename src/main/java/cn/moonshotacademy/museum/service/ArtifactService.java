package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtifactService {

    @Autowired
    private ArtifactRepository artifactRepository;

    public ArtifactEntity getArtifactById(int id) {
        ArtifactEntity artifact = artifactRepository.findById(id) //AI说这里返回的是Optional<ArtifactEntity>，需要orElseThrow
                .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));
        System.out.println(artifact.getFiles());
        return artifact;
    }

    public int createEmptyArtifact(){
        ArtifactEntity emptyArtifact = new ArtifactEntity();
        emptyArtifact = artifactRepository.save(emptyArtifact);
        return emptyArtifact.getId();
    }

    public String createThumbnailedPicture(String inputFilePath) {
        String outputFilePath = "Thumbnailed" + "_" + inputFilePath;

        return outputFilePath;
    }
}