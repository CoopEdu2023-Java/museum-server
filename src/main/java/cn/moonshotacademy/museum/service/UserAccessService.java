package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.entity.UserEntity;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAccessService {

    @Autowired private UserRepository userRepository;

    public UserDetails loadUserByUsername(int userId) throws UsernameNotFoundException {
        UserEntity user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));

        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "", // 未存储用户密码
                authorities);
    }
}
