package com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.Memorandum.MemorandumService;
import com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto.MemorandumRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto.MemorandumResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/memorandums")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class MemorandumController {

    @Autowired
    private MemorandumService service;
    @Autowired
    private MemorandumService memorandumService;

    @PostMapping("/generar")
    public ResponseEntity<MemorandumResponseDTO> generar(@RequestBody MemorandumRequestDTO request) {
        return ResponseEntity.ok(memorandumService.guardarMemorandum(request));
    }

    @GetMapping("/listar")
public ResponseEntity<List<MemorandumResponseDTO>> listar() {
    try {
        List<MemorandumResponseDTO> lista = service.listarMemorandum();
        return ResponseEntity.ok(lista);
    } catch (Exception e) {
        e.printStackTrace(); 
        throw e;
    }
}

@GetMapping("pendientesacuse/area/{idArea}")
public ResponseEntity<List<MemorandumResponseDTO>> listarPorArea(@PathVariable Long idArea) {
    try {
        List<MemorandumResponseDTO> lista = service.listarPorArea(idArea);
        return ResponseEntity.ok(lista);
    } catch (Exception e) {
        e.printStackTrace(); 
        throw e; 
    }
}

@GetMapping("/{id}")
public ResponseEntity<MemorandumResponseDTO> obtenerPorId(@PathVariable Long id) {
    return ResponseEntity.ok(service.buscarPorId(id));
}
}
