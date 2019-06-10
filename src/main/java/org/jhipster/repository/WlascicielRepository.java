package org.jhipster.repository;

import org.jhipster.domain.Wlasciciel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wlasciciel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WlascicielRepository extends JpaRepository<Wlasciciel, Long> {

}
