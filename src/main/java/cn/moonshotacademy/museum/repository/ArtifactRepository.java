package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.ArtifactEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Integer> {
    List<ArtifactEntity> findByCompetency(String competency);
    @Query("SELECT a FROM ArtifactEntity a LEFT JOIN FETCH a.userList u WHERE a.isDeleted = FALSE")
    Page<ArtifactEntity> findArtifactsByUsername(Pageable pageable);

    @Query(
        value =
                "SELECT * FROM artifact WHERE "
                        + "(is_deleted = FALSE) AND ("
                        + "(to_tsvector('english', title) @@ plainto_tsquery('english', :keyword)) "
                        + "OR (title ILIKE ALL (array(SELECT '%' || k || '%' "
                        + "FROM unnest(string_to_array(:keyword, ' ')) AS k))) "
                        + ") "
                        + "ORDER BY ts_rank(to_tsvector('english', title), plainto_tsquery('english', :keyword)) DESC",
        countQuery =
                "SELECT count(*) FROM artifact WHERE "
                        + "(is_deleted = FALSE) AND ("
                        + "(to_tsvector('english', title) @@ plainto_tsquery('english', :keyword)) "
                        + "OR (title ILIKE ALL (array(SELECT '%' || k || '%' "
                        + "FROM unnest(string_to_array(:keyword, ' ')) AS k))) "
                        + ")",
        nativeQuery = true)
    Page<ArtifactEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
