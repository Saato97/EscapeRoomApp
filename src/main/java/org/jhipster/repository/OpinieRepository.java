package org.jhipster.repository;

import org.jhipster.domain.Opinie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Opinie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpinieRepository extends JpaRepository<Opinie, Long> {

}
