package cn.moonshotacademy.museum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Entity
@Table(name = "artifact")
@Data
public class ArtifactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String intro;
    private String competency;
    private boolean isDeleted = false;
    private String avatarUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_artifact_rel",
            joinColumns = @JoinColumn(name = "artifact_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> userList = new HashSet<>();
}
