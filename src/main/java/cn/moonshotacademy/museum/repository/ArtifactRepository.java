package cn.moonshotacademy.museum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.moonshotacademy.museum.entity.ArtifactEntity;

public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Long> {
    Optional<ArtifactEntity> findById(Integer id);
}   
