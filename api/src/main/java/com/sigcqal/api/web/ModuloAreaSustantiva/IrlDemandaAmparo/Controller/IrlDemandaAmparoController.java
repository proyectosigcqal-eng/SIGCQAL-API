package com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Controller;

import com.sigcqal.api.application.ModuloAreaSustantiva.IrlDemandaAmparo.IrlDemandaAmparoService;
import com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/irl-demanda-amparo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IrlDemandaAmparoController {

    private final IrlDemandaAmparoService service;

    // Guardar datos del formulario
    @PostMapping
    public ResponseEntity<IrlDemandaAmparoResponseDTO> guardar(
            @RequestBody IrlDemandaAmparoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(request));
    }

    // Generar el DOCX de la demanda
    @PostMapping("/{id}/generar-demanda")
    public ResponseEntity<IrlDemandaAmparoResponseDTO> generarDemanda(
            @PathVariable Integer id) {
        return ResponseEntity.ok(service.generarDemanda(id));
    }

    // Descargar el DOCX de la demanda (se genera al vuelo con los datos actuales)
    @GetMapping("/{id}/descargar")
    public ResponseEntity<byte[]> descargarPorId(@PathVariable Integer id) {
        return service.obtenerArchivoPorId(id)
            .map(archivo -> ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + archivo.nombreArchivo() + "\"")
                .body(archivo.contenido()))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 2.3.4 — Cargar PDF demanda presentada + acuse
    @PostMapping("/{id}/cargar-demanda")
    public ResponseEntity<IrlDemandaAmparoResponseDTO> cargarDemanda(
            @PathVariable Integer id,
            @RequestParam("demanda") MultipartFile demandaPdf,
            @RequestParam(value = "acuse", required = false) MultipartFile acusePdf) {
        return ResponseEntity.ok(service.cargarDemandaPresentada(id, demandaPdf, acusePdf));
    }

    // 2.3.3 — Semáforo judicial
    @GetMapping({"/{id}/semaforo-judicial", "/semaforo-judicial"})
    public ResponseEntity<SemaforoJudicialDTO> semaforo(
            @PathVariable(required = false) Integer id,
            @RequestParam(value = "id", required = false) Integer idQuery) {
        Integer idDemanda = id != null ? id : idQuery;
        if (idDemanda == null) {
            throw new IllegalArgumentException("Se requiere el id de la demanda en la ruta o como query param.");
        }
        return ResponseEntity.ok(service.obtenerSemaforo(idDemanda));
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<IrlDemandaAmparoResponseDTO> buscarPorId(
            @PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Buscar por expediente
    @GetMapping("/expediente/{idExpediente}")
    public ResponseEntity<IrlDemandaAmparoResponseDTO> buscarPorExpediente(
            @PathVariable Integer idExpediente) {
        return ResponseEntity.ok(service.buscarPorExpediente(idExpediente));
    }
}