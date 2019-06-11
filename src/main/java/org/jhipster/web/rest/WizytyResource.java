package org.jhipster.web.rest;

import org.jhipster.domain.Wizyty;
import org.jhipster.repository.WizytyRepository;
import org.jhipster.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.jhipster.domain.Wizyty}.
 */
@RestController
@RequestMapping("/api")
public class WizytyResource {

    private final Logger log = LoggerFactory.getLogger(WizytyResource.class);

    private static final String ENTITY_NAME = "wizyty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WizytyRepository wizytyRepository;

    public WizytyResource(WizytyRepository wizytyRepository) {
        this.wizytyRepository = wizytyRepository;
    }

    /**
     * {@code POST  /wizyties} : Create a new wizyty.
     *
     * @param wizyty the wizyty to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wizyty, or with status {@code 400 (Bad Request)} if the wizyty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wizyties")
    public ResponseEntity<Wizyty> createWizyty(@RequestBody Wizyty wizyty) throws URISyntaxException {
        log.debug("REST request to save Wizyty : {}", wizyty);
        if (wizyty.getId() != null) {
            throw new BadRequestAlertException("A new wizyty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wizyty result = wizytyRepository.save(wizyty);
        return ResponseEntity.created(new URI("/api/wizyties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wizyties} : Updates an existing wizyty.
     *
     * @param wizyty the wizyty to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wizyty,
     * or with status {@code 400 (Bad Request)} if the wizyty is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wizyty couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wizyties")
    public ResponseEntity<Wizyty> updateWizyty(@RequestBody Wizyty wizyty) throws URISyntaxException {
        log.debug("REST request to update Wizyty : {}", wizyty);
        if (wizyty.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Wizyty result = wizytyRepository.save(wizyty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wizyty.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wizyties} : get all the wizyties.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wizyties in body.
     */
    @GetMapping("/wizyties")
    public ResponseEntity<List<Wizyty>> getAllWizyties(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Wizyties");
        Page<Wizyty> page = wizytyRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wizyties/:id} : get the "id" wizyty.
     *
     * @param id the id of the wizyty to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wizyty, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wizyties/{id}")
    public ResponseEntity<Wizyty> getWizyty(@PathVariable Long id) {
        log.debug("REST request to get Wizyty : {}", id);
        Optional<Wizyty> wizyty = wizytyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wizyty);
    }

    /**
     * {@code DELETE  /wizyties/:id} : delete the "id" wizyty.
     *
     * @param id the id of the wizyty to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wizyties/{id}")
    public ResponseEntity<Void> deleteWizyty(@PathVariable Long id) {
        log.debug("REST request to delete Wizyty : {}", id);
        wizytyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
