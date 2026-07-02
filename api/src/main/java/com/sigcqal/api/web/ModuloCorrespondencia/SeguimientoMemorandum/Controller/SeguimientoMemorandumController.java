package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.application.ModuloCorrespondencia.SeguimientoMemorandum.SeguimientoMemorandumService;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto.SeguimientoMemorandumRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto.SeguimientoMemorandumResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seguimiento-memorandum")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SeguimientoMemorandumController {

    private final SeguimientoMemorandumService service;

    @PostMapping(value = "/guardar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SeguimientoMemorandumResponseDTO> guardar(
            @ModelAttribute SeguimientoMemorandumRequestDTO request) {
        return ResponseEntity.ok(service.guardar(request));
    }

    @PostMapping(value = "/{idSeguimientoMemorandum}/adjunto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SeguimientoMemorandumResponseDTO> subirAdjunto(
            @PathVariable Long idSeguimientoMemorandum,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @RequestParam(value = "archivoAdjunto", required = false) MultipartFile archivoAdjunto) {
        MultipartFile file = (archivo != null && !archivo.isEmpty()) ? archivo : archivoAdjunto;
        return ResponseEntity.ok(service.guardarAdjunto(idSeguimientoMemorandum, file));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SeguimientoMemorandumResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/memorandum/{idMemo}")
    public ResponseEntity<List<SeguimientoMemorandumResponseDTO>> listarPorMemorandum(
            @PathVariable Long idMemo) {
        return ResponseEntity.ok(service.listarPorMemorandumId(idMemo));
    }

    @PutMapping("/concluir/{idSeguimiento}")
    public ResponseEntity<Void> concluir(
            @PathVariable Long idSeguimiento,
            @RequestBody SeguimientoMemorandumRequestDTO request) {
        service.concluir(idSeguimiento, request);
        return ResponseEntity.ok().build();
    }
}
