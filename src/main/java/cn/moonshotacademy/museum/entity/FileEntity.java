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

    private int artifactId;
    private String name;
    private String url;
    private String type;
    private boolean isMain = false;
    private boolean isDeleted = false;

    public FileEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public FileEntity(String name, String url, String type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }
}
