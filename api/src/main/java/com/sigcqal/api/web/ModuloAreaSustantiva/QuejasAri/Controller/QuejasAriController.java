package com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.application.ModuloAreaSustantiva.QuejasAri.QuejasAriApplicationService;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto.QuejasAriContextoDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto.QuejasAriRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto.QuejasAriResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/quejas-ari")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuejasAriController {

    @Autowired
    private QuejasAriApplicationService service;

    @PostMapping("/generar")
    public ResponseEntity<QuejasAriResponseDTO> generar(@RequestBody QuejasAriRequestDTO request) {
        return ResponseEntity.ok(service.guardarQuejasAri(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<QuejasAriResponseDTO>> listar() {
        try {
            List<QuejasAriResponseDTO> lista = service.listarQuejasAri();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/por-queja/{idQueja}")
    public ResponseEntity<List<QuejasAriResponseDTO>> listarPorQueja(@PathVariable Long idQueja) {
        try {
            List<QuejasAriResponseDTO> lista = service.listarPorIdQueja(idQueja);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/contexto/{folio}")
    public ResponseEntity<QuejasAriContextoDTO> obtenerContexto(@PathVariable String folio) {
        return ResponseEntity.ok(service.obtenerContextoPorFolio(folio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuejasAriResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizar(
        @PathVariable Long id,
        @RequestParam("archivo") MultipartFile archivo
    ) throws IOException {
        service.finalizarAri(id, archivo.getBytes());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/folio/{folio}/descargar")
public ResponseEntity<byte[]> descargarPorFolio(@PathVariable String folio) {
    return service.obtenerArchivoPorFolio(folio)
        .map(archivo -> ResponseEntity.ok()
            .contentType(org.springframework.http.MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
            .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + archivo.nombreArchivo() + "\"")
            .body(archivo.contenido()))
        .orElseGet(() -> ResponseEntity.notFound().build());
}
}