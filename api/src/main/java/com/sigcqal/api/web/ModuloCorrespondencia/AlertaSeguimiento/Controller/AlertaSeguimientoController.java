package com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.service.ConsultarDetalleFolioService;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.service.ConsultarFoliosConRetrasoService;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.service.ConsultarHistorialAlertasService;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.service.EnviarAlertaSeguimientoService;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AlertaSeguimiento;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso;
import com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto.AlertaEnviadaResponse;
import com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto.DetalleFolioResponse;
import com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto.EnviarAlertaRequest;
import com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto.FolioConRetrasoResponse;
import com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto.HistorialAlertaResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alertas")
public class AlertaSeguimientoController {
    @Autowired
    private ConsultarFoliosConRetrasoService consultarFoliosConRetrasoService;

    @Autowired
    private ConsultarDetalleFolioService consultarDetalleFolioService;

    @Autowired
    private EnviarAlertaSeguimientoService enviarAlertaSeguimientoService;

    @Autowired
    private ConsultarHistorialAlertasService consultarHistorialAlertasService;

    @GetMapping("/folios-con-retraso")
    public ResponseEntity<List<FolioConRetrasoResponse>> listarFoliosConRetraso(
            @RequestParam(required = false) Long idArea,
            @RequestParam(required = false) String numFolio,
            @RequestParam(required = false) String dependencia,
            @RequestParam(required = false) String estatus,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate fechaFin
    ) {
        List<FolioConRetraso> folios = consultarFoliosConRetrasoService.consultar(estatus, idArea, fechaInicio, fechaFin, numFolio, dependencia);
        List<FolioConRetrasoResponse> response = folios.stream().map(this::toFolioConRetrasoResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/folios/{idFolio}/detalle")
    public ResponseEntity<DetalleFolioResponse> obtenerDetalle(@PathVariable Long idFolio) {
        FolioConRetraso detalle = consultarDetalleFolioService.consultarDetalle(idFolio);
        return ResponseEntity.ok(toDetalleResponse(detalle));
    }

    @PostMapping
    public ResponseEntity<AlertaEnviadaResponse> enviarAlerta(
            @RequestHeader(name = "X-Id-Usuario") Long idUsuario,
            @Valid @RequestBody EnviarAlertaRequest request
    ) {
        AlertaSeguimiento alerta = enviarAlertaSeguimientoService.enviar(request.getIdFolio(), request.getMensajeUrgencia(), idUsuario);

        AlertaEnviadaResponse response = new AlertaEnviadaResponse();
        response.setIdAlerta(alerta.getIdAlerta());
        response.setFolioUnico(alerta.getFolioUnico());
        response.setAreaDestinataria(alerta.getNombreAreaDestinataria());
        response.setFechaEnvio(alerta.getFechaEnvio());
        response.setMensaje("Alerta enviada correctamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/folios/{idFolio}/historial")
    public ResponseEntity<List<HistorialAlertaResponse>> obtenerHistorial(@PathVariable Long idFolio) {
        List<AlertaSeguimiento> alertas = consultarHistorialAlertasService.consultarPorFolio(idFolio);
        List<HistorialAlertaResponse> response = alertas.stream().map(this::toHistorialResponse).toList();
        return ResponseEntity.ok(response);
    }

    private FolioConRetrasoResponse toFolioConRetrasoResponse(FolioConRetraso domain) {
        FolioConRetrasoResponse dto = new FolioConRetrasoResponse();
        dto.setIdFolio(domain.getIdFolio());
        dto.setFolioUnico(domain.getFolioUnico());
        dto.setNumOficio(domain.getNumOficioExterno());
        dto.setRemitente(buildRemitente(domain.getDependenciaRemitente(), domain.getNombreRemitente()));
        dto.setAreaResponsable(domain.getNombreAreaResponsable());
        dto.setEstatus(domain.getNombreEstatus());
        dto.setFechaRegistro(domain.getFechaRegistro());
        dto.setDiasAtraso(domain.getDiasAtraso());
        return dto;
    }

    private DetalleFolioResponse toDetalleResponse(FolioConRetraso domain) {
        DetalleFolioResponse dto = new DetalleFolioResponse();
        dto.setIdFolio(domain.getIdFolio());
        dto.setFolioUnico(domain.getFolioUnico());
        dto.setNumOficioExterno(domain.getNumOficioExterno());
        dto.setDependenciaRemitente(domain.getDependenciaRemitente());
        dto.setNombreRemitente(domain.getNombreRemitente());
        dto.setIdAreaResponsable(domain.getIdAreaResponsable());
        dto.setNombreAreaResponsable(domain.getNombreAreaResponsable());
        dto.setEstatusActual(domain.getNombreEstatus());
        dto.setFechaRegistro(domain.getFechaRegistro());
        dto.setDiasAtraso(domain.getDiasAtraso());
        return dto;
    }

    private HistorialAlertaResponse toHistorialResponse(AlertaSeguimiento domain) {
        HistorialAlertaResponse dto = new HistorialAlertaResponse();
        dto.setIdAlerta(domain.getIdAlerta());
        dto.setUsuarioEmisor(domain.getNombreUsuarioEmisor());
        dto.setAreaDestinataria(domain.getNombreAreaDestinataria());
        dto.setMensajeUrgencia(domain.getMensajeUrgencia());
        dto.setFechaEnvio(domain.getFechaEnvio());
        dto.setDiasAtrasoAlMomento(domain.getDiasAtraso());
        return dto;
    }

    private String buildRemitente(String dependencia, String remitente) {
        if (dependencia == null || dependencia.isBlank()) return remitente;
        if (remitente == null || remitente.isBlank()) return dependencia;
        return dependencia + " - " + remitente;
    }
}

