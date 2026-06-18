package com.sigcqal.api.web.ModuloAreaSustantiva.OficioNotificacion.Controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sigcqal.api.application.ModuloAreaSustantiva.OficioNotificacion.OficioNotificacionService;
import com.sigcqal.api.domain.ModuloAreaSustantiva.OficioNotificacion.Model.OficioNotificacion;
import com.sigcqal.api.web.ModuloAreaSustantiva.OficioNotificacion.Dto.OficioNotificacionHistorialDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.OficioNotificacion.Dto.OficioNotificacionResponseDTO;

@RestController
@RequestMapping("/api/v1/oficio-notificacion")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OficioNotificacionController {

    private final OficioNotificacionService service;

    @PostMapping("/generar")
    public ResponseEntity<OficioNotificacionResponseDTO> generar(
            @RequestParam("folioExpediente")     String  folioExpediente,
            @RequestParam("numOficio")           String  numOficio,
            @RequestParam("idAutoridad")         Integer idAutoridad,
            @RequestParam("nombreAutoridad")     String  nombreAutoridad,
            @RequestParam("tipoAcuerdo")         String  tipoAcuerdo,
            @RequestParam("nombreContribuyente") String  nombreContribuyente,
            @RequestParam("fechaAcuerdo")        String  fechaAcuerdo,
            @RequestParam(value = "fundamento",      required = false) String fundamento,
            @RequestParam(value = "inicialesAsesor", required = false) String inicialesAsesor
    ) {
        return ResponseEntity.ok(service.generarOficio(
        folioExpediente,
        numOficio,
        idAutoridad,
        nombreAutoridad,
        nombreContribuyente,
        fechaAcuerdo,      // ← fechaAcuerdo antes de fundamento
        fundamento,
        inicialesAsesor,
        tipoAcuerdo        // ← tipoAcuerdo al final
));
    }

   @GetMapping("/{folio}/historial")
public ResponseEntity<List<OficioNotificacionHistorialDTO>> historial(
        @PathVariable String folio) {

    List<OficioNotificacionHistorialDTO> lista = service.listarPorFolio(folio)
        .stream()
        .map(this::toHistorialDTO)
        .toList();

    return ResponseEntity.ok(lista);
}

private OficioNotificacionHistorialDTO toHistorialDTO(OficioNotificacion o) {
    return OficioNotificacionHistorialDTO.builder()
        .id(o.getId())
        .numOficio(o.getNumOficio())
        .fechaAcuerdo(o.getFechaAcuerdo())
        .nombreAutoridad(o.getNombreAutoridad())
        .rutaPdf(o.getRutaPdf())
        .fechaGeneracion(o.getFechaGeneracion())
        .build();
}
}