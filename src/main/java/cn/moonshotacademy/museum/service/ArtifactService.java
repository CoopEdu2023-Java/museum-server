package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.repository.ArtifactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtifactService {

    @Autowired
    private ArtifactRepository artifactRepository;

    @Transactional
    public ArtifactEntity updateArtifact(Integer artifactId, String title, String intro, String competency, String category, String type) {
        // 查找 artifact 是否存在
        ArtifactEntity artifact = artifactRepository.findById(artifactId)
            .orElseThrow(() -> new RuntimeException("Artifact not found"));

        System.out.println("Before update: " + artifact);

        
        if (title != null) {
            artifact.setTitle(title);  
        }
        if (intro != null) {
            artifact.setIntro(intro);  
        }
        if (competency != null) {
            artifact.setCompetency(competency);
        }
        if (category != null) {
            artifact.setCategory(category); 
        }
        if (type != null) {
            artifact.setType(type);  
        }
        System.out.println("After update: " + artifact);
        return artifactRepository.save(artifact); 
    }
}