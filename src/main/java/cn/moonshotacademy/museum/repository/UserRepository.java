package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}