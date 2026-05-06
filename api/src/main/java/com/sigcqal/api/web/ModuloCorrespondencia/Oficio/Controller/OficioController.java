package com.sigcqal.api.web.ModuloCorrespondencia.Oficio.Controller;

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

import com.sigcqal.api.application.ModuloCorrespondencia.Oficio.OficioService;
import com.sigcqal.api.web.ModuloCorrespondencia.Oficio.Dto.OficioRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Oficio.Dto.OficioResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/oficios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class OficioController {

    @Autowired
    private OficioService service;
    @Autowired
    private OficioService oficioService;

    @PostMapping("/generar")
    public ResponseEntity<OficioResponseDTO> generar(@RequestBody OficioRequestDTO request) {
        return ResponseEntity.ok(oficioService.guardarOficio(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<OficioResponseDTO>> listar() {
        try {
            List<OficioResponseDTO> lista = service.listarOficio();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace(); 
            throw e;
        }
    }

    @GetMapping("pendientesacuse/area/{idArea}")
    public ResponseEntity<List<OficioResponseDTO>> listarPorArea(@PathVariable Long idArea) {
        try {
            List<OficioResponseDTO> lista = service.listarPorArea(idArea);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace(); 
            throw e; 
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OficioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizar(
        @PathVariable Long id,
        @RequestParam("archivo") MultipartFile archivo,
        @RequestParam("idArea") Long idArea  
    ) throws IOException {
        oficioService.finalizarAsignacion(id, archivo.getBytes(), idArea);
        return ResponseEntity.ok().build();
    }
}
