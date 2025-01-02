package cn.moonshotacademy.museum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
// import java.util.UUID;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="artifact_id")
    private Integer artifactId;
    private String name;
    private String url;
    private String type;
    private boolean isMain = false;
    private boolean isDeleted = false;

    public FileEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
