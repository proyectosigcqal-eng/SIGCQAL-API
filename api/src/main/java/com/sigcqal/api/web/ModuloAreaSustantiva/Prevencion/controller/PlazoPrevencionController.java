// package com.sigcqal.api.web.ModuloAreaSustantiva.Prevencion.controller;

// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import com.sigcqal.api.application.ModuloAreaSustantiva.Prevencion.PlazoPrevencionService;
// import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.PlazoPrevencion.Model.PlazoPrevencion;
// import com.sigcqal.api.web.ModuloAreaSustantiva.Prevencion.Dto.PlazoPrevencionResponseDTO;


// @RestController
// @RequestMapping("/api/v1/expedientes")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "*")
// public class PlazoPrevencionController {

//     private final PlazoPrevencionService service;

//     @GetMapping("/{folio}/plazo-prevencion")
//     public ResponseEntity<PlazoPrevencionResponseDTO> obtenerPlazo(
//             @PathVariable String folio) {

//         PlazoPrevencion plazo = service.calcularPlazo(folio);

//         PlazoPrevencionResponseDTO dto = PlazoPrevencionResponseDTO.builder()
//                 .folioExpediente(plazo.getFolioExpediente())
//                 .fechaInicio(plazo.getFechaInicio().toString())
//                 .fechaLimite(plazo.getFechaLimite().toString())
//                 .diasHabilesRestantes(plazo.getDiasHabilesRestantes())
//                 .semaforoEstado(plazo.getSemaforoEstado())
//                 .vencido(plazo.getVencido())
//                 .build();

//         return ResponseEntity.ok(dto);
//     }
// }
