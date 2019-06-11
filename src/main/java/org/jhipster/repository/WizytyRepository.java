package org.jhipster.repository;

import org.jhipster.domain.Wizyty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Wizyty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WizytyRepository extends JpaRepository<Wizyty, Long> {

    @Query("select wizyty from Wizyty wizyty where wizyty.user.login = ?#{principal.username}")
    Page<Wizyty> findByUserIsCurrentUser(Pageable pageable);

}
