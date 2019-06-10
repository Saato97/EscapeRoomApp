package org.jhipster.repository;

import org.jhipster.domain.Wizyty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wizyty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WizytyRepository extends JpaRepository<Wizyty, Long> {

}
