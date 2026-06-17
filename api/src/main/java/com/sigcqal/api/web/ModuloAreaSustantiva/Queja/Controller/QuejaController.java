package com.sigcqal.api.web.ModuloAreaSustantiva.Queja.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloAreaSustantiva.Queja.QuejaService;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.Queja.DTO.QuejaResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.Queja.DTO.RequisitosRequestDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/quejas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuejaController {

    private final QuejaService service;
    private final QuejaJPARepository repository; // Para el GET de requisitos, que es un caso especial

    @GetMapping("/{idQueja}")
    public ResponseEntity<QuejaResponseDTO> obtenerPorId(@PathVariable Integer idQueja) {
        return ResponseEntity.ok(service.obtenerPorId(idQueja));
    }

    @GetMapping
    public ResponseEntity<List<QuejaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
    @PostMapping("/{folio}/admitir")
    public ResponseEntity<String> admitir(@PathVariable String folio) {
        service.admitirQueja(folio);
        return ResponseEntity.ok("Queja admitida correctamente.");
    }

    // El frontend llama esto cuando hace clic en REQUIERE ACLARACIÓN
    @PostMapping("/{folio}/requerir-aclaracion")
    public ResponseEntity<String> requerirAclaracion(@PathVariable String folio) {
        service.requerirAclaracion(folio);
        return ResponseEntity.ok("Queja regresada a aclaración.");
    }

    @PatchMapping("/{folio}/requisitos")
public ResponseEntity<Void> actualizarRequisitos(
        @PathVariable String folio,
        @RequestBody  RequisitosRequestDTO body) {
    service.actualizarRequisitos(
        folio,
        body.getRequisitoIdentificacion(),
        body.getRequisitoActosFiscales(),
        body.getRequisitoNarrativaClara(),
        body.getRequisitoCompetenciaCedecon()
    );
    return ResponseEntity.ok().build();
}

@GetMapping("/{folio}/requisitos")
public ResponseEntity<RequisitosRequestDTO> obtenerRequisitos(
        @PathVariable String folio) {

    List<Object[]> resultados = repository.findRequisitosByFolio(folio);

    if (resultados == null || resultados.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Object[] row = resultados.get(0);

    if (row == null || row.length < 4) {
        return ResponseEntity.notFound().build();
    }

    RequisitosRequestDTO dto = new RequisitosRequestDTO();
    dto.setRequisitoIdentificacion(row[0] != null && (Boolean) row[0]);
    dto.setRequisitoActosFiscales (row[1] != null && (Boolean) row[1]);
    dto.setRequisitoNarrativaClara(row[2] != null && (Boolean) row[2]);
    dto.setRequisitoCompetenciaCedecon(row[3] != null && (Boolean) row[3]);
    return ResponseEntity.ok(dto);
}
}