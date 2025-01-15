package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Integer> {
}
