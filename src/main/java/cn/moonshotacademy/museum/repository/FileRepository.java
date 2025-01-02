package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.FileEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findById(int id);

    boolean existsById(int id);

    List<FileEntity> findByIdIn(List<Integer> userIds);
}
