package com.sa.apirest.healthplan.controller;

import com.sa.apirest.healthplan.interfaces.BaseController;
import com.sa.apirest.healthplan.exception.HealthPlanMismatchException;
import com.sa.apirest.healthplan.exception.HealthPlanNotFoundException;
import com.sa.apirest.healthplan.exception.NotValidBodyException;
import com.sa.apirest.healthplan.exception.UnknownErrorException;
import com.sa.apirest.healthplan.model.Base;
import com.sa.apirest.healthplan.service.BaseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public abstract class BaseControllerImpl<E extends Base, S extends BaseServiceImpl<E, Integer>> implements BaseController<E, Integer> {

    S service;

    public BaseControllerImpl(S service) {
        this.service = service;
    }

    @Override
    @GetMapping("")
    @Operation(
            description = "Trae todos los registros",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "200", ref = "okAPI"),
                    @ApiResponse(responseCode = "401", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )
    public ResponseEntity<?> getAllRecord() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/paged")
    @Operation(
            description = "Trae una cantidad limitada de registros",
            parameters = {
                    @Parameter(name = "pageable", description = "Objeto pageable", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", ref = "okAPI"),
                    @ApiResponse(responseCode = "401", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )
    public ResponseEntity<?> getAllRecord(Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/{id}")
    @Operation(
            description = "Trae un registro por id",
            parameters = {
                    @Parameter(name = "id", description = "Id del registro", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", ref = "okAPI"),
                    @ApiResponse(responseCode = "401", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "404", ref = "notFoundAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )
    public ResponseEntity<?> getRecordById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (EntityNotFoundException e) {
            throw new HealthPlanNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("")
    @Operation(
            description = "Crea un registro",
            parameters = {
                    @Parameter(name = "entity", description = "Entidad que se guardada", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "201", ref = "createdAPI"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "401", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )
    public ResponseEntity<?> save(@RequestBody E entity) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
        } catch (DataIntegrityViolationException e) {
            throw new NotValidBodyException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PutMapping(value = {"", "/{id}"})
    @Operation(
            description = "Actualiza un registro",
            parameters = {
                    @Parameter(name = "id", description = "Id del registro", required = false),
                    @Parameter(name = "entity", description = "Entidad actualizada", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", ref = "updatedAPI"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "401", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "404", ref = "notFoundAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )
    public ResponseEntity<?> update(@PathVariable(required = false) Integer id, @RequestBody E entity) {
        try {
            if (id == null) {
                id = entity.getId();
            }
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity));
        } catch (EntityNotFoundException e) {
            throw new HealthPlanNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            throw new HealthPlanMismatchException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            throw new NotValidBodyException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
            description = "Elimina un registro",
            parameters = {
                    @Parameter(name = "id", description = "Id del registro", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", ref = "noContentAPI"),
                    @ApiResponse(responseCode = "401", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "404", ref = "notFoundAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        } catch (EntityNotFoundException e) {
            throw new HealthPlanNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
