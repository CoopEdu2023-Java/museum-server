package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.entity.UserEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired private UserRepository userRepository;

    public void checkTeacherPermission(int userId) {
        UserEntity user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));

        if (!"teacher".equalsIgnoreCase(user.getType())) {
            throw new BusinessException(ExceptionEnum.UNAUTHORIZED);
        }
    }
}
