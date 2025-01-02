package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}