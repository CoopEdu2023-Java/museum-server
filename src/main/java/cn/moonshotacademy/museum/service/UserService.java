package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.AvatarDto;

import java.io.IOException;

public interface UserService {
    public void uploadUserAvatar(AvatarDto requestData, int userId) throws IOException;
}
