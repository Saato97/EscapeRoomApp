package org.jhipster.repository;

import org.jhipster.domain.Klient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Klient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlientRepository extends JpaRepository<Klient, Long> {

}
