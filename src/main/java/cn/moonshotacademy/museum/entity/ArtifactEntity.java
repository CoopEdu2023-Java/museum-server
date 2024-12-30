package cn.moonshotacademy.museum.entity;

import jakarta.persistence.Entity;
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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artifact")
@Data
@NoArgsConstructor
public class ArtifactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String avatar_url;
    private String intro;
    private String competency;
    private boolean isDeleted = false;

    @ManyToMany
    @JoinTable(
            name = "user_artifact_rel",
            joinColumns = @JoinColumn(name = "artifact_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users = new HashSet<>();
}
