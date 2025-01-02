package cn.moonshotacademy.museum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.moonshotacademy.museum.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Integer id);
}   
