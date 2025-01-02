package cn.moonshotacademy.museum.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "artifact")
public class ArtifactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String intro;
    private String competency;
    private boolean isDeleted = false;
    private String avatarUrl;
    private String category;
    private String type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_artifact_rel",  // 关系表名
        joinColumns = @JoinColumn(name = "artifact_id"),  // Artifact 表的外键
        inverseJoinColumns = @JoinColumn(name = "user_id")  // User 表的外键
    )
    
    private Set<UserEntity> userList = new HashSet<>();
}
