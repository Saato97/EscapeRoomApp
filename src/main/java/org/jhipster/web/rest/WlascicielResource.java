package org.jhipster.web.rest;

import org.jhipster.domain.Wlasciciel;
import org.jhipster.repository.WlascicielRepository;
import org.jhipster.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.jhipster.domain.Wlasciciel}.
 */
@RestController
@RequestMapping("/api")
public class WlascicielResource {

    private final Logger log = LoggerFactory.getLogger(WlascicielResource.class);

    private static final String ENTITY_NAME = "wlasciciel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WlascicielRepository wlascicielRepository;

    public WlascicielResource(WlascicielRepository wlascicielRepository) {
        this.wlascicielRepository = wlascicielRepository;
    }

    /**
     * {@code POST  /wlasciciels} : Create a new wlasciciel.
     *
     * @param wlasciciel the wlasciciel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wlasciciel, or with status {@code 400 (Bad Request)} if the wlasciciel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wlasciciels")
    public ResponseEntity<Wlasciciel> createWlasciciel(@RequestBody Wlasciciel wlasciciel) throws URISyntaxException {
        log.debug("REST request to save Wlasciciel : {}", wlasciciel);
        if (wlasciciel.getId() != null) {
            throw new BadRequestAlertException("A new wlasciciel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wlasciciel result = wlascicielRepository.save(wlasciciel);
        return ResponseEntity.created(new URI("/api/wlasciciels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wlasciciels} : Updates an existing wlasciciel.
     *
     * @param wlasciciel the wlasciciel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wlasciciel,
     * or with status {@code 400 (Bad Request)} if the wlasciciel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wlasciciel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wlasciciels")
    public ResponseEntity<Wlasciciel> updateWlasciciel(@RequestBody Wlasciciel wlasciciel) throws URISyntaxException {
        log.debug("REST request to update Wlasciciel : {}", wlasciciel);
        if (wlasciciel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Wlasciciel result = wlascicielRepository.save(wlasciciel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wlasciciel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wlasciciels} : get all the wlasciciels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wlasciciels in body.
     */
    @GetMapping("/wlasciciels")
    public List<Wlasciciel> getAllWlasciciels() {
        log.debug("REST request to get all Wlasciciels");
        return wlascicielRepository.findAll();
    }

    /**
     * {@code GET  /wlasciciels/:id} : get the "id" wlasciciel.
     *
     * @param id the id of the wlasciciel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wlasciciel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wlasciciels/{id}")
    public ResponseEntity<Wlasciciel> getWlasciciel(@PathVariable Long id) {
        log.debug("REST request to get Wlasciciel : {}", id);
        Optional<Wlasciciel> wlasciciel = wlascicielRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wlasciciel);
    }

    /**
     * {@code DELETE  /wlasciciels/:id} : delete the "id" wlasciciel.
     *
     * @param id the id of the wlasciciel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wlasciciels/{id}")
    public ResponseEntity<Void> deleteWlasciciel(@PathVariable Long id) {
        log.debug("REST request to delete Wlasciciel : {}", id);
        wlascicielRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
