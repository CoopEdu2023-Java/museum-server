package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.dto.LoginRequestDto;
import cn.moonshotacademy.museum.entity.UserEntity;
import java.io.IOException;

public interface UserService {
    public void uploadUserAvatar(AvatarDto requestData, int userId) throws IOException;

    public void deleteUserAvatar(int userId);

    public UserEntity findLearner(String name) throws IOException;

    public UserEntity findInstructor(String name) throws IOException;

    public String login(LoginRequestDto loginRequestDto);
}
