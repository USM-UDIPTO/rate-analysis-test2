package com.dxc.eproc.rateanalysis.web.rest;

import com.dxc.eproc.rateanalysis.repository.WorkEstimateLeadRepository;
import com.dxc.eproc.rateanalysis.service.WorkEstimateLeadService;
import com.dxc.eproc.rateanalysis.service.dto.WorkEstimateLeadDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dxc.eproc.rateanalysis.domain.WorkEstimateLead}.
 */
@RestController
@RequestMapping("/api")
public class WorkEstimateLeadResource {

    private final Logger log = LoggerFactory.getLogger(WorkEstimateLeadResource.class);

    private static final String ENTITY_NAME = "rateAnalysisWorkEstimateLead";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEstimateLeadService workEstimateLeadService;

    private final WorkEstimateLeadRepository workEstimateLeadRepository;

    public WorkEstimateLeadResource(
        WorkEstimateLeadService workEstimateLeadService,
        WorkEstimateLeadRepository workEstimateLeadRepository
    ) {
        this.workEstimateLeadService = workEstimateLeadService;
        this.workEstimateLeadRepository = workEstimateLeadRepository;
    }

    /**
     * {@code POST  /work-estimate-leads} : Create a new workEstimateLead.
     *
     * @param workEstimateLeadDTO the workEstimateLeadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEstimateLeadDTO, or with status {@code 400 (Bad Request)} if the workEstimateLead has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-estimate-leads")
    public ResponseEntity<WorkEstimateLeadDTO> createWorkEstimateLead(@Valid @RequestBody WorkEstimateLeadDTO workEstimateLeadDTO)
        throws URISyntaxException {
        log.debug("REST request to save WorkEstimateLead : {}", workEstimateLeadDTO);
        if (workEstimateLeadDTO.getId() != null) {
            throw new BadRequestAlertException("A new workEstimateLead cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEstimateLeadDTO result = workEstimateLeadService.save(workEstimateLeadDTO);
        return ResponseEntity
            .created(new URI("/api/work-estimate-leads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-estimate-leads/:id} : Updates an existing workEstimateLead.
     *
     * @param id the id of the workEstimateLeadDTO to save.
     * @param workEstimateLeadDTO the workEstimateLeadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEstimateLeadDTO,
     * or with status {@code 400 (Bad Request)} if the workEstimateLeadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEstimateLeadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-estimate-leads/{id}")
    public ResponseEntity<WorkEstimateLeadDTO> updateWorkEstimateLead(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkEstimateLeadDTO workEstimateLeadDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WorkEstimateLead : {}, {}", id, workEstimateLeadDTO);
        if (workEstimateLeadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workEstimateLeadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workEstimateLeadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkEstimateLeadDTO result = workEstimateLeadService.save(workEstimateLeadDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEstimateLeadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-estimate-leads/:id} : Partial updates given fields of an existing workEstimateLead, field will ignore if it is null
     *
     * @param id the id of the workEstimateLeadDTO to save.
     * @param workEstimateLeadDTO the workEstimateLeadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEstimateLeadDTO,
     * or with status {@code 400 (Bad Request)} if the workEstimateLeadDTO is not valid,
     * or with status {@code 404 (Not Found)} if the workEstimateLeadDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the workEstimateLeadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/work-estimate-leads/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<WorkEstimateLeadDTO> partialUpdateWorkEstimateLead(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WorkEstimateLeadDTO workEstimateLeadDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkEstimateLead partially : {}, {}", id, workEstimateLeadDTO);
        if (workEstimateLeadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workEstimateLeadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workEstimateLeadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkEstimateLeadDTO> result = workEstimateLeadService.partialUpdate(workEstimateLeadDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEstimateLeadDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /work-estimate-leads} : get all the workEstimateLeads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEstimateLeads in body.
     */
    @GetMapping("/work-estimate-leads")
    public ResponseEntity<List<WorkEstimateLeadDTO>> getAllWorkEstimateLeads(Pageable pageable) {
        log.debug("REST request to get a page of WorkEstimateLeads");
        Page<WorkEstimateLeadDTO> page = workEstimateLeadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-estimate-leads/:id} : get the "id" workEstimateLead.
     *
     * @param id the id of the workEstimateLeadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEstimateLeadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-estimate-leads/{id}")
    public ResponseEntity<WorkEstimateLeadDTO> getWorkEstimateLead(@PathVariable Long id) {
        log.debug("REST request to get WorkEstimateLead : {}", id);
        Optional<WorkEstimateLeadDTO> workEstimateLeadDTO = workEstimateLeadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workEstimateLeadDTO);
    }

    /**
     * {@code DELETE  /work-estimate-leads/:id} : delete the "id" workEstimateLead.
     *
     * @param id the id of the workEstimateLeadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-estimate-leads/{id}")
    public ResponseEntity<Void> deleteWorkEstimateLead(@PathVariable Long id) {
        log.debug("REST request to delete WorkEstimateLead : {}", id);
        workEstimateLeadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}