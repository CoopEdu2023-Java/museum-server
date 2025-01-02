package cn.moonshotacademy.museum.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/avatars/add")
    public ResponseDto<Void> uploadUserAvatar(@PathVariable int userId, @ModelAttribute AvatarDto image) throws IOException {
            userService.uploadUserAvatar(image, userId);
            return ResponseDto.success();
    }
}
