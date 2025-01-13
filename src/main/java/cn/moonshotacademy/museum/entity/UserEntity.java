package cn.moonshotacademy.museum.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "\"user\"") // 使用双引号转义，或更改为 'users'
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String role;

    @Column(name = "name_default")
    private String nameDefault;

    @Column(name = "name_en")
    private String nameEn;

    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String intro;

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude // 防止递归
    @JsonBackReference
    private Set<ArtifactEntity> artifacts = new HashSet<>();

    public UserEntity(String email, String defaultName, String englishName, String intro) {
        this.email = email;
        this.nameDefault = defaultName;
        this.nameEn = englishName;
        this.intro = intro;
    }
}
