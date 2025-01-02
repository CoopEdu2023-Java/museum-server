package cn.moonshotacademy.museum.repository;

import cn.moonshotacademy.museum.entity.ArtifactEntity;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Integer> {
    Optional<ArtifactEntity> findById(Integer id);
    @Query("SELECT a FROM ArtifactEntity a LEFT JOIN FETCH a.userList u WHERE a.isDeleted = FALSE")
    Page<ArtifactEntity> findArtifactsByUsername(Pageable pageable);

    @Query(
            value =
                    "SELECT * FROM artifact WHERE "
                            + "(to_tsvector('english', title) @@ plainto_tsquery('english', :keyword)) "
                            + "OR (title ILIKE ALL (array(SELECT '%' || k || '%' "
                            + "FROM unnest(string_to_array(:keyword, ' ')) AS k))) "
                            + "ORDER BY ts_rank(to_tsvector('english', title), plainto_tsquery('english', :keyword)) DESC",
            countQuery =
                    "SELECT count(*) FROM artifact WHERE "
                            + "(to_tsvector('english', title) @@ plainto_tsquery('english', :keyword)) "
                            + "OR (title ILIKE ALL (array(SELECT '%' || k || '%' "
                            + "FROM unnest(string_to_array(:keyword, ' ')) AS k)))",
            nativeQuery = true)
    Page<ArtifactEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}