package cn.moonshotacademy.museum.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "file")
@Getter
@Setter
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String url;
    private String type;

    @Column(name = "is_main")
    private String isMain;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artifact_id")
    @ToString.Exclude // 防止递归
    @JsonBackReference
    private ArtifactEntity artifact;

    public FileEntity(String name, String url, String type, String isMain, boolean isDeleted) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.isMain = isMain;
        this.isDeleted = isDeleted;
    }
}
