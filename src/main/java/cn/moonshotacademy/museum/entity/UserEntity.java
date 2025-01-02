package cn.moonshotacademy.museum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"user\"") // PostgreSQL 中避免使用保留字
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String role;
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

    @ManyToMany(mappedBy = "userList")  
    private Set<ArtifactEntity> artifactList = new HashSet<>();
}
