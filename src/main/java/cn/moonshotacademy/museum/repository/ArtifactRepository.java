package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Long> {
    @Query("SELECT a FROM ArtifactEntity a LEFT JOIN FETCH a.userList u WHERE a.isDeleted = FALSE")
    Page<ArtifactEntity> findArtifactsByUsername(Pageable pageable);
}
