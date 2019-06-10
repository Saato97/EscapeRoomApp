package org.jhipster.web.rest;

import org.jhipster.domain.Klient;
import org.jhipster.repository.KlientRepository;
import org.jhipster.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.jhipster.domain.Klient}.
 */
@RestController
@RequestMapping("/api")
public class KlientResource {

    private final Logger log = LoggerFactory.getLogger(KlientResource.class);

    private static final String ENTITY_NAME = "klient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KlientRepository klientRepository;

    public KlientResource(KlientRepository klientRepository) {
        this.klientRepository = klientRepository;
    }

    /**
     * {@code POST  /klients} : Create a new klient.
     *
     * @param klient the klient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new klient, or with status {@code 400 (Bad Request)} if the klient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/klients")
    public ResponseEntity<Klient> createKlient(@Valid @RequestBody Klient klient) throws URISyntaxException {
        log.debug("REST request to save Klient : {}", klient);
        if (klient.getId() != null) {
            throw new BadRequestAlertException("A new klient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Klient result = klientRepository.save(klient);
        return ResponseEntity.created(new URI("/api/klients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /klients} : Updates an existing klient.
     *
     * @param klient the klient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klient,
     * or with status {@code 400 (Bad Request)} if the klient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the klient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/klients")
    public ResponseEntity<Klient> updateKlient(@Valid @RequestBody Klient klient) throws URISyntaxException {
        log.debug("REST request to update Klient : {}", klient);
        if (klient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Klient result = klientRepository.save(klient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, klient.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /klients} : get all the klients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of klients in body.
     */
    @GetMapping("/klients")
    public List<Klient> getAllKlients() {
        log.debug("REST request to get all Klients");
        return klientRepository.findAll();
    }

    /**
     * {@code GET  /klients/:id} : get the "id" klient.
     *
     * @param id the id of the klient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the klient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/klients/{id}")
    public ResponseEntity<Klient> getKlient(@PathVariable Long id) {
        log.debug("REST request to get Klient : {}", id);
        Optional<Klient> klient = klientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(klient);
    }

    /**
     * {@code DELETE  /klients/:id} : delete the "id" klient.
     *
     * @param id the id of the klient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/klients/{id}")
    public ResponseEntity<Void> deleteKlient(@PathVariable Long id) {
        log.debug("REST request to delete Klient : {}", id);
        klientRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
