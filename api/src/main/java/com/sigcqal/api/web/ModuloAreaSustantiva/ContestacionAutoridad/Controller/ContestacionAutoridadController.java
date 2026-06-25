package com.sigcqal.api.web.ModuloAreaSustantiva.ContestacionAutoridad.Controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.application.ModuloAreaSustantiva.ContestacionAutoridad.ContestacionAutoridadService;
import com.sigcqal.api.web.ModuloAreaSustantiva.ContestacionAutoridad.Dto.ContestacionAutoridadResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contestacion-autoridad")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContestacionAutoridadController {

    private final ContestacionAutoridadService service;

   @PostMapping(value = "/guardar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<ContestacionAutoridadResponseDTO> guardar(
        @RequestParam("folioExpediente")  String folioExpediente,
        @RequestParam("numeroOficio")     String numeroOficio,
        @RequestParam(value = "nombreTitular",  required = false) String nombreTitular,
        @RequestParam(value = "observaciones",  required = false) String observaciones,
        @RequestParam("decision")         String decision,
        @RequestParam(value = "archivoPDF", required = false) MultipartFile archivoPDF
) throws IOException {
    byte[] bytes = archivoPDF != null ? archivoPDF.getBytes() : null;
    String nombre = archivoPDF != null
        ? "CONTESTACION_" + folioExpediente + "_" + System.currentTimeMillis() + ".pdf"
        : null;
    return ResponseEntity.ok(
        service.guardar(folioExpediente, numeroOficio, nombreTitular,
                       observaciones, decision, bytes, nombre)
    );
}

}
