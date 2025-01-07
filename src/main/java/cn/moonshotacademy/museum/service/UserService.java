package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.entity.UserEntity;

import java.io.IOException;

public interface UserService {
    public void uploadUserAvatar(AvatarDto requestData, int userId) throws IOException;
    public UserEntity findLearner(String name) throws IOException;
    public UserEntity findInstructor(String name) throws IOException;
}
