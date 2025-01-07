package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByDefaultName(String username);

    Optional<UserEntity> findByEnglishName(String username);

    UserEntity findByEmail(String email);
}
