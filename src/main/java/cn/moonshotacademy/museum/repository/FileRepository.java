package cn.moonshotacademy.museum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.moonshotacademy.museum.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByName(String filename);
}   
