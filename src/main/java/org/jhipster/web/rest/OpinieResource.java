package org.jhipster.web.rest;

import org.jhipster.domain.Opinie;
import org.jhipster.repository.OpinieRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link org.jhipster.domain.Opinie}.
 */
@RestController
@RequestMapping("/api")
public class OpinieResource {

    private final Logger log = LoggerFactory.getLogger(OpinieResource.class);

    private static final String ENTITY_NAME = "opinie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpinieRepository opinieRepository;

    public OpinieResource(OpinieRepository opinieRepository) {
        this.opinieRepository = opinieRepository;
    }

    /**
     * {@code POST  /opinies} : Create a new opinie.
     *
     * @param opinie the opinie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opinie, or with status {@code 400 (Bad Request)} if the opinie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opinies")
    public ResponseEntity<Opinie> createOpinie(@Valid @RequestBody Opinie opinie) throws URISyntaxException {
        log.debug("REST request to save Opinie : {}", opinie);
        if (opinie.getId() != null) {
            throw new BadRequestAlertException("A new opinie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Opinie result = opinieRepository.save(opinie);
        return ResponseEntity.created(new URI("/api/opinies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opinies} : Updates an existing opinie.
     *
     * @param opinie the opinie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opinie,
     * or with status {@code 400 (Bad Request)} if the opinie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opinie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opinies")
    public ResponseEntity<Opinie> updateOpinie(@Valid @RequestBody Opinie opinie) throws URISyntaxException {
        log.debug("REST request to update Opinie : {}", opinie);
        if (opinie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Opinie result = opinieRepository.save(opinie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opinie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /opinies} : get all the opinies.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opinies in body.
     */
    @GetMapping("/opinies")
    public ResponseEntity<List<Opinie>> getAllOpinies(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false) String filter) {
        if ("wizyty-is-null".equals(filter)) {
            log.debug("REST request to get all Opinies where wizyty is null");
            return new ResponseEntity<>(StreamSupport
                .stream(opinieRepository.findAll().spliterator(), false)
                .filter(opinie -> opinie.getWizyty() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Opinies");
        Page<Opinie> page = opinieRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opinies/:id} : get the "id" opinie.
     *
     * @param id the id of the opinie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opinie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opinies/{id}")
    public ResponseEntity<Opinie> getOpinie(@PathVariable Long id) {
        log.debug("REST request to get Opinie : {}", id);
        Optional<Opinie> opinie = opinieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(opinie);
    }

    /**
     * {@code DELETE  /opinies/:id} : delete the "id" opinie.
     *
     * @param id the id of the opinie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opinies/{id}")
    public ResponseEntity<Void> deleteOpinie(@PathVariable Long id) {
        log.debug("REST request to delete Opinie : {}", id);
        opinieRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
