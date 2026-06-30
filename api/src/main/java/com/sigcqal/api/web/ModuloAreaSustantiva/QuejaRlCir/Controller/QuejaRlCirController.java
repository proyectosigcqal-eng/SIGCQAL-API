package com.sigcqal.api.web.ModuloAreaSustantiva.QuejaRlCir.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.ModuloAreaSustantiva.QuejaRlCir.QuejaRlCirApplicationService;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejaRlCir.Dto.QuejaRlCirRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejaRlCir.Dto.QuejaRlCirResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/queja-rl-cir")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuejaRlCirController {

    @Autowired
    private QuejaRlCirApplicationService service;

    @PostMapping("/generar")
    public ResponseEntity<QuejaRlCirResponseDTO> generar(@RequestBody QuejaRlCirRequestDTO request) {
        return ResponseEntity.ok(service.guardarQuejaRlCir(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<QuejaRlCirResponseDTO>> listar() {
        try {
            List<QuejaRlCirResponseDTO> lista = service.listarQuejaRlCir();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{id}/descargar")
    public ResponseEntity<byte[]> descargarPorId(@PathVariable Long id) {
        return service.obtenerArchivoPorId(id)
            .map(archivo -> ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + archivo.nombreArchivo() + "\"")
                .body(archivo.contenido()))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}