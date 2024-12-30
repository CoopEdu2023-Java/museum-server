package cn.moonshotacademy.museum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file")
@Data
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int artifact_id;
    private String name;
    private String url;
    private String type;
    private String is_main;
    private boolean isDeleted;

    public FileEntity(
            int id,
            int artifact_id,
            String name,
            String url,
            String type,
            String is_main,
            boolean isDeleted) {
        this.id = id;
        this.artifact_id = artifact_id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.is_main = is_main;
        this.isDeleted = isDeleted;
    }
}
