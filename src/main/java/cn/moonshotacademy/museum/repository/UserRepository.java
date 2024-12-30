package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(int id);

    boolean existsById(int id);

    List<UserEntity> findByIdIn(List<Integer> userIds);
}
