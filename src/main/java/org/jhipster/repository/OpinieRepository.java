package org.jhipster.repository;

import org.jhipster.domain.Opinie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Opinie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpinieRepository extends JpaRepository<Opinie, Long> {
    @Query("select opinie from Opinie opinie where opinie.wizyty.user.login = ?#{principal.username}")
    Page<Opinie> findByUserIsCurrentUser(Pageable pageable);

}
