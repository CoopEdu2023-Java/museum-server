      
package cn.moonshotacademy.museum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"user\"")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String role;
    private String email;
    private String defaultName;
    private String englishName;
    private String intro;
    private String avatarUrl;

    public UserEntity(String email, String defaultName, String englishName, String intro) {
        this.email = email;
        this.defaultName = defaultName;
        this.englishName = englishName;
        this.intro = intro;
    }
}

    