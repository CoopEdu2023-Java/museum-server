package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.AvatarDto;
import cn.moonshotacademy.museum.dto.LoginRequestDto;
import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.entity.UserEntity;
import cn.moonshotacademy.museum.service.UserService;
import java.io.IOException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/avatars/add")
    public ResponseDto<Void> uploadUserAvatar(
            @PathVariable int userId, @ModelAttribute AvatarDto image) throws IOException {
        userService.uploadUserAvatar(image, userId);
        return ResponseDto.success();
    }

    @DeleteMapping("/{userId}/avatars/delete")
    public ResponseDto<Void> deleteUserAvatar(@PathVariable int userId) {
        userService.deleteUserAvatar(userId);
        return ResponseDto.success();
    }
    
    @GetMapping("/find/learner")
    public ResponseDto<UserEntity> findLearner(@RequestParam String name) throws IOException {
        UserEntity user = userService.findLearner(name);
        return ResponseDto.success(user);
    }

    @GetMapping("/find/instructor")
    public ResponseDto<UserEntity> findInstructor(@RequestParam String name) throws IOException {
        UserEntity user = userService.findInstructor(name);
        return ResponseDto.success(user);
    }

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        System.out.println(123123);
        System.out.println(loginRequestDto.getName());
        System.out.println(loginRequestDto.getEmail());
        String token = userService.login(loginRequestDto);
        return ResponseDto.success(token);
    }
    
}
