package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends CrudRepository<ArtifactEntity, Integer> {
    // boolean existsByUsername(String username);
}
