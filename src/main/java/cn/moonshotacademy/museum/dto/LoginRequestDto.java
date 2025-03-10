package cn.moonshotacademy.museum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {
    private String name;
    private String email;
    
}
