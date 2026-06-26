package com.sigcqal.api.web.ModuloAreaSustantiva.RLCir.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sigcqal.api.application.ModuloAreaSustantiva.RLCir.RLCirApplicationService;
import com.sigcqal.api.web.ModuloAreaSustantiva.RLCir.Dto.RLCirRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.RLCir.Dto.RLCirResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rl-cir")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RLCirController {

    @Autowired
    private RLCirApplicationService service;

    @PostMapping("/generar")
    public ResponseEntity<RLCirResponseDTO> generar(@RequestBody RLCirRequestDTO request) {
        return ResponseEntity.ok(service.guardarRLCir(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RLCirResponseDTO>> listar() {
        try {
            List<RLCirResponseDTO> lista = service.listarRLCir();
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