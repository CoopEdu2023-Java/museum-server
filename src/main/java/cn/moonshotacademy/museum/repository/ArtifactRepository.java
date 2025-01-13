package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Integer> {
    Optional<ArtifactEntity> findById(int id);

    boolean existsById(int id);

    List<ArtifactEntity> findByIdIn(List<Integer> userIds);
}
