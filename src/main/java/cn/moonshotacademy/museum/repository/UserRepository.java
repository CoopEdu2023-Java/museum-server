package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    UserEntity findByUsernameAndPassword(String username, String password);
    UserEntity findByEmailAndPassword(String email, String password);
    UserEntity findByUsernameAndEmail(String username, String email);
    UserEntity findByUsernameAndEmailAndPassword(String username, String email, String password);
}
