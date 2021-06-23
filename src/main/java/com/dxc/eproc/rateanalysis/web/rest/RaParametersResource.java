package com.dxc.eproc.rateanalysis.web.rest;

import com.dxc.eproc.rateanalysis.repository.RaParametersRepository;
import com.dxc.eproc.rateanalysis.service.RaParametersService;
import com.dxc.eproc.rateanalysis.service.dto.RaParametersDTO;
import com.dxc.eproc.rateanalysis.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dxc.eproc.rateanalysis.domain.RaParameters}.
 */
@RestController
@RequestMapping("/api")
public class RaParametersResource {

    private final Logger log = LoggerFactory.getLogger(RaParametersResource.class);

    private static final String ENTITY_NAME = "rateAnalysisRaParameters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RaParametersService raParametersService;

    private final RaParametersRepository raParametersRepository;

    public RaParametersResource(RaParametersService raParametersService, RaParametersRepository raParametersRepository) {
        this.raParametersService = raParametersService;
        this.raParametersRepository = raParametersRepository;
    }

    /**
     * {@code POST  /ra-parameters} : Create a new raParameters.
     *
     * @param raParametersDTO the raParametersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raParametersDTO, or with status {@code 400 (Bad Request)} if the raParameters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ra-parameters")
    public ResponseEntity<RaParametersDTO> createRaParameters(@Valid @RequestBody RaParametersDTO raParametersDTO)
        throws URISyntaxException {
        log.debug("REST request to save RaParameters : {}", raParametersDTO);
        if (raParametersDTO.getId() != null) {
            throw new BadRequestAlertException("A new raParameters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RaParametersDTO result = raParametersService.save(raParametersDTO);
        return ResponseEntity
            .created(new URI("/api/ra-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ra-parameters/:id} : Updates an existing raParameters.
     *
     * @param id the id of the raParametersDTO to save.
     * @param raParametersDTO the raParametersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raParametersDTO,
     * or with status {@code 400 (Bad Request)} if the raParametersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raParametersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ra-parameters/{id}")
    public ResponseEntity<RaParametersDTO> updateRaParameters(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RaParametersDTO raParametersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RaParameters : {}, {}", id, raParametersDTO);
        if (raParametersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raParametersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raParametersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RaParametersDTO result = raParametersService.save(raParametersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, raParametersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ra-parameters/:id} : Partial updates given fields of an existing raParameters, field will ignore if it is null
     *
     * @param id the id of the raParametersDTO to save.
     * @param raParametersDTO the raParametersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raParametersDTO,
     * or with status {@code 400 (Bad Request)} if the raParametersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the raParametersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the raParametersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ra-parameters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RaParametersDTO> partialUpdateRaParameters(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RaParametersDTO raParametersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RaParameters partially : {}, {}", id, raParametersDTO);
        if (raParametersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raParametersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raParametersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RaParametersDTO> result = raParametersService.partialUpdate(raParametersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, raParametersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ra-parameters} : get all the raParameters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of raParameters in body.
     */
    @GetMapping("/ra-parameters")
    public List<RaParametersDTO> getAllRaParameters() {
        log.debug("REST request to get all RaParameters");
        return raParametersService.findAll();
    }

    /**
     * {@code GET  /ra-parameters/:id} : get the "id" raParameters.
     *
     * @param id the id of the raParametersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raParametersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ra-parameters/{id}")
    public ResponseEntity<RaParametersDTO> getRaParameters(@PathVariable Long id) {
        log.debug("REST request to get RaParameters : {}", id);
        Optional<RaParametersDTO> raParametersDTO = raParametersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(raParametersDTO);
    }

    /**
     * {@code DELETE  /ra-parameters/:id} : delete the "id" raParameters.
     *
     * @param id the id of the raParametersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ra-parameters/{id}")
    public ResponseEntity<Void> deleteRaParameters(@PathVariable Long id) {
        log.debug("REST request to delete RaParameters : {}", id);
        raParametersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}