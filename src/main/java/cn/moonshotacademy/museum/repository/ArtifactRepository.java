package cn.moonshotacademy.museum.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cn.moonshotacademy.museum.entity.ArtifactEntity;

@Repository
public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Integer> {
}