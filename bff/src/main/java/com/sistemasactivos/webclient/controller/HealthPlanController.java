/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasactivos.webclient.controller;

import com.sistemasactivos.webclient.interfaces.IHealthPlanService;
import com.sistemasactivos.webclient.model.HealthPlan;
import com.sistemasactivos.webclient.utils.TokenVerificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthplans")
public class HealthPlanController {

    @Autowired
    private IHealthPlanService healthPlanService;

    @Autowired
    private TokenVerificator tokenVerificator;

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization-token") String authorizationToken) {
        tokenVerificator.verifyToken(authorizationToken);
        return ResponseEntity.status(HttpStatus.OK).body(healthPlanService.findAll());
    }


    @GetMapping("/paged")
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "50") Integer size,
                                     @RequestParam(required = false, defaultValue = "") String sort,
                                     @RequestHeader("Authorization-token") String authorizationToken) {
        tokenVerificator.verifyToken(authorizationToken);
        return ResponseEntity.status(HttpStatus.OK).body(healthPlanService.findAllPaged(page, size, sort));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id, @RequestHeader("Authorization-token") String authorizationToken) {
        tokenVerificator.verifyToken(authorizationToken);
        return ResponseEntity.status(HttpStatus.OK).body(healthPlanService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String filter, @RequestHeader("Authorization-token") String authorizationToken) {
        tokenVerificator.verifyToken(authorizationToken);
        return ResponseEntity.status(HttpStatus.OK).body(healthPlanService.search(filter));
    }

    @GetMapping("/searchPaged")
    public ResponseEntity<?> searchPaged(@RequestParam(required = false, defaultValue = "") String filter,
                                        @RequestParam(required = false, defaultValue = "0") Integer page,
                                        @RequestParam(required = false, defaultValue = "50") Integer size,
                                        @RequestParam(required = false, defaultValue = "") String sort,
                                         @RequestHeader("Authorization-token") String authorizationToken) {
        tokenVerificator.verifyToken(authorizationToken);
        return ResponseEntity.status(HttpStatus.OK).body(healthPlanService.searchPaged(filter, page, size, sort));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody HealthPlan user, @RequestHeader("Authorization-token") String authorizationToken) {
        tokenVerificator.verifyToken(authorizationToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(healthPlanService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody HealthPlan healthPlan, @RequestHeader("Authorization-token") String authorizationToken) {
        tokenVerificator.verifyToken(authorizationToken);
        return ResponseEntity.status(HttpStatus.OK).body(healthPlanService.update(id, healthPlan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestHeader("Authorization-token") String authorizationToken) {
        tokenVerificator.verifyToken(authorizationToken);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(healthPlanService.delete(id));
    }
}