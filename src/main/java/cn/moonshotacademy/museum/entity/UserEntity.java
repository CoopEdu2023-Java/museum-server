package cn.moonshotacademy.museum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String role;
    private String name_default;
    private String name_en;
    private String email;
    private String avatar_url;
    private String intro;

    public UserEntity(String email, String defaultName, String englishName, String intro) {
        this.email = email;
        this.name_default = defaultName;
        this.name_en = englishName;
        this.intro = intro;
    }
}
