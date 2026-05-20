package com.sigcqal.api.web.ModuloCorrespondencia.OficioContestacionExterna.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.OficioContestacionExterna.OficioContestacionExternaService;
import com.sigcqal.api.web.ModuloCorrespondencia.OficioContestacionExterna.Dto.OficioContestacionExternaDTOs;

@RestController
@RequestMapping("/api/v1/oficio-contestacion-externa")
@CrossOrigin(origins = "*")
public class OficioContestacionExternaController {
    @Autowired
    private OficioContestacionExternaService service;

    @PostMapping
    public ResponseEntity<OficioContestacionExternaDTOs.Response> guardar(@RequestBody(required = false) OficioContestacionExternaDTOs.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(request));
    }

    @GetMapping("/correspondencia/{idCorrespondencia}")
    public ResponseEntity<OficioContestacionExternaDTOs.Response> buscarPorCorrespondencia(@PathVariable Long idCorrespondencia) {
        return service.buscarPorCorrespondencia(idCorrespondencia)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok().build());
    }

    @GetMapping("/listar")
    public ResponseEntity<List<OficioContestacionExternaDTOs.Response>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}
