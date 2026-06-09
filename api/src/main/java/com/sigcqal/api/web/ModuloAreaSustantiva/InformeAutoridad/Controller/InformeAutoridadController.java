package com.sigcqal.api.web.ModuloAreaSustantiva.InformeAutoridad.Controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.application.ModuloAreaSustantiva.InformeAutoridad.InformeAutoridadService;
import com.sigcqal.api.web.ModuloAreaSustantiva.InformeAutoridad.Dto.PlazoInformeAutoridadResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.InformeAutoridad.Dto.RecepcionInformeResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/expedientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InformeAutoridadController {

    private final InformeAutoridadService service;

    @GetMapping("/{folio}/plazo-informe-autoridad")
    public ResponseEntity<PlazoInformeAutoridadResponseDTO> obtenerPlazo(@PathVariable String folio) {
        return ResponseEntity.ok(service.obtenerPlazo(folio));
    }

    @PostMapping(path = "/{folio}/informe-autoridad", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecepcionInformeResponseDTO> registrarInforme(
            @PathVariable String folio,
            @RequestParam("fecha_recepcion") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaRecepcion,
            @RequestParam("numero_oficio_respuesta") String numeroOficioRespuesta,
            @RequestParam("fojas") Integer fojas,
            @RequestParam(value = "observaciones", required = false) String observaciones,
            @RequestParam("archivo_pdf") MultipartFile archivoPdf,
            HttpServletRequest request) {

        String ipCliente = request.getHeader("X-Forwarded-For");
        if (ipCliente == null || ipCliente.isBlank()) {
            ipCliente = request.getRemoteAddr();
        } else {
            ipCliente = ipCliente.split(",")[0].trim();
        }

        return ResponseEntity.ok(service.registrarInforme(
                folio,
                fechaRecepcion,
                numeroOficioRespuesta,
                fojas,
                observaciones,
                ipCliente,
                archivoPdf));
    }
}

