package org.jhipster.repository;

import org.jhipster.domain.EscapeRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the EscapeRoom entity.
 */
@Repository
public interface EscapeRoomRepository extends JpaRepository<EscapeRoom, Long> {

    @Query(value = "select distinct escapeRoom from EscapeRoom escapeRoom left join fetch escapeRoom.wlasciciels",
        countQuery = "select count(distinct escapeRoom) from EscapeRoom escapeRoom")
    Page<EscapeRoom> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct escapeRoom from EscapeRoom escapeRoom left join fetch escapeRoom.wlasciciels")
    List<EscapeRoom> findAllWithEagerRelationships();

    @Query("select escapeRoom from EscapeRoom escapeRoom left join fetch escapeRoom.wlasciciels where escapeRoom.id =:id")
    Optional<EscapeRoom> findOneWithEagerRelationships(@Param("id") Long id);

}
