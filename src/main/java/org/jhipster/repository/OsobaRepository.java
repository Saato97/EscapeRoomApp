package org.jhipster.repository;

import org.jhipster.domain.Osoba;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Osoba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OsobaRepository extends JpaRepository<Osoba, Long> {

}
