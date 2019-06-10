package org.jhipster.web.rest;

import org.jhipster.domain.EscapeRoom;
import org.jhipster.repository.EscapeRoomRepository;
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

/**
 * REST controller for managing {@link org.jhipster.domain.EscapeRoom}.
 */
@RestController
@RequestMapping("/api")
public class EscapeRoomResource {

    private final Logger log = LoggerFactory.getLogger(EscapeRoomResource.class);

    private static final String ENTITY_NAME = "escapeRoom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EscapeRoomRepository escapeRoomRepository;

    public EscapeRoomResource(EscapeRoomRepository escapeRoomRepository) {
        this.escapeRoomRepository = escapeRoomRepository;
    }

    /**
     * {@code POST  /escape-rooms} : Create a new escapeRoom.
     *
     * @param escapeRoom the escapeRoom to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new escapeRoom, or with status {@code 400 (Bad Request)} if the escapeRoom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/escape-rooms")
    public ResponseEntity<EscapeRoom> createEscapeRoom(@Valid @RequestBody EscapeRoom escapeRoom) throws URISyntaxException {
        log.debug("REST request to save EscapeRoom : {}", escapeRoom);
        if (escapeRoom.getId() != null) {
            throw new BadRequestAlertException("A new escapeRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EscapeRoom result = escapeRoomRepository.save(escapeRoom);
        return ResponseEntity.created(new URI("/api/escape-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /escape-rooms} : Updates an existing escapeRoom.
     *
     * @param escapeRoom the escapeRoom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escapeRoom,
     * or with status {@code 400 (Bad Request)} if the escapeRoom is not valid,
     * or with status {@code 500 (Internal Server Error)} if the escapeRoom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/escape-rooms")
    public ResponseEntity<EscapeRoom> updateEscapeRoom(@Valid @RequestBody EscapeRoom escapeRoom) throws URISyntaxException {
        log.debug("REST request to update EscapeRoom : {}", escapeRoom);
        if (escapeRoom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EscapeRoom result = escapeRoomRepository.save(escapeRoom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, escapeRoom.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /escape-rooms} : get all the escapeRooms.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of escapeRooms in body.
     */
    @GetMapping("/escape-rooms")
    public ResponseEntity<List<EscapeRoom>> getAllEscapeRooms(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of EscapeRooms");
        Page<EscapeRoom> page;
        if (eagerload) {
            page = escapeRoomRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = escapeRoomRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /escape-rooms/:id} : get the "id" escapeRoom.
     *
     * @param id the id of the escapeRoom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the escapeRoom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/escape-rooms/{id}")
    public ResponseEntity<EscapeRoom> getEscapeRoom(@PathVariable Long id) {
        log.debug("REST request to get EscapeRoom : {}", id);
        Optional<EscapeRoom> escapeRoom = escapeRoomRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(escapeRoom);
    }

    /**
     * {@code DELETE  /escape-rooms/:id} : delete the "id" escapeRoom.
     *
     * @param id the id of the escapeRoom to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/escape-rooms/{id}")
    public ResponseEntity<Void> deleteEscapeRoom(@PathVariable Long id) {
        log.debug("REST request to delete EscapeRoom : {}", id);
        escapeRoomRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
