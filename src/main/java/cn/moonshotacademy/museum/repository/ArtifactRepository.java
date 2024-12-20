package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Long> {
    Page<ArtifactEntity> findByIsDeletedFalse(Pageable pageable);
}
