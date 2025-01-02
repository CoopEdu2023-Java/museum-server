package cn.moonshotacademy.museum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artifact")
@Data
@NoArgsConstructor
public class ArtifactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String title;

    private String avatar_url;

    private String intro;

    private String competency;

    private String type;

    private Boolean isDeleted;
}
