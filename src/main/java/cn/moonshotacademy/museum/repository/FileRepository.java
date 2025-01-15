package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.FileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findByName(String filename);
}
