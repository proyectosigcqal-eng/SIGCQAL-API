package com.sigcqal.api.application.ModuloCorrespondencia.SeguimientoMemorandum;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Model.SeguimientoMemorandum;
import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Port.ISeguimientoMemorandumPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Mapper.SeguimientoMemorandumMapper;
import com.sigcqal.api.infra.exception.FileStorageException;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto.SeguimientoMemorandumRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto.SeguimientoMemorandumResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeguimientoMemorandumService {

    @Autowired
    private ISeguimientoMemorandumPort port;

    @Autowired
    private SeguimientoMemorandumMapper mapper;

    @Autowired
    private FileUploadPort fileUploadPort;

    public SeguimientoMemorandumResponseDTO guardar(SeguimientoMemorandumRequestDTO request) {
        SeguimientoMemorandum seguimiento = mapearDesdeRequest(request);
        var saved = port.guardar(seguimiento);

        if (request.getArchivoAdjunto() != null && !request.getArchivoAdjunto().isEmpty()) {
            saved = guardarArchivoAdjunto(saved.getIdSeguimientoMemorandum(), request.getArchivoAdjunto());
        }

        return mapper.toResponse(saved);
    }

    public SeguimientoMemorandumResponseDTO guardarAdjunto(Long idSeguimientoMemorandum, MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("Debe enviar un archivo PDF en el campo 'archivo' o 'archivoAdjunto'");
        }
        SeguimientoMemorandum actualizado = guardarArchivoAdjunto(idSeguimientoMemorandum, archivo);
        return mapper.toResponse(actualizado);
    }

    private SeguimientoMemorandum guardarArchivoAdjunto(Long idSeguimientoMemorandum, MultipartFile archivo) {
        SeguimientoMemorandum seguimiento = port.buscarPorId(idSeguimientoMemorandum)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado: " + idSeguimientoMemorandum));

        String nombreBase = resolverNombreArchivo(seguimiento);
        String nombreArchivo = nombreBase.endsWith(".pdf") ? nombreBase : nombreBase + ".pdf";

        try {
            String url = fileUploadPort.guardarArchivoSeguimientoMemorandum(archivo.getBytes(), nombreArchivo);
            seguimiento.setArchivoAdjunto(url);
            return port.actualizar(seguimiento);
        } catch (IOException e) {
            throw new FileStorageException("Error al subir el documento firmado: " + e.getMessage(), e);
        }
    }

    private String resolverNombreArchivo(SeguimientoMemorandum seguimiento) {
        if (seguimiento.getFolioFormateado() != null && !seguimiento.getFolioFormateado().isBlank()) {
            return seguimiento.getFolioFormateado();
        }
        if (seguimiento.getFolioRespuesta() != null) {
            int anio = seguimiento.getFechaRegistro() != null
                    ? seguimiento.getFechaRegistro().getYear()
                    : LocalDate.now().getYear();
            return String.format("CM-%06d-%d", seguimiento.getFolioRespuesta(), anio);
        }
        return "SM-" + seguimiento.getIdSeguimientoMemorandum();
    }

    private SeguimientoMemorandum mapearDesdeRequest(SeguimientoMemorandumRequestDTO request) {
        SeguimientoMemorandum seguimiento = new SeguimientoMemorandum();

        seguimiento.setIdSeguimientoMemorandum(request.getIdSeguimientoMemorandum());
        seguimiento.setIdMemo(request.getIdMemo());
        seguimiento.setRespuestaSeguimientoMemorandum(request.getRespuestaSeguimientoMemorandum());
        seguimiento.setIdUsuario(request.getIdUsuario());
        seguimiento.setIdEstatus(request.getIdEstatus());

        if (request.getFechaResolucion() != null && !request.getFechaResolucion().isBlank()) {
            seguimiento.setFechaResolucion(LocalDate.parse(request.getFechaResolucion()));
        }
        if (request.getHoraResolucion() != null && !request.getHoraResolucion().isBlank()) {
            seguimiento.setHoraResolucion(LocalTime.parse(request.getHoraResolucion()));
        }

        seguimiento.setFechaRegistro(LocalDateTime.now());
        return seguimiento;
    }

    public List<SeguimientoMemorandumResponseDTO> listarTodos() {
        return port.listarTodos()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SeguimientoMemorandumResponseDTO> listarPorMemorandumId(Long idMemo) {
        return port.listarPorMemorandumId(idMemo)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public void concluir(Long idSeguimiento, SeguimientoMemorandumRequestDTO request) {
        SeguimientoMemorandum seguimiento = port.buscarPorId(idSeguimiento)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado: " + idSeguimiento));

        seguimiento.setIdEstatus(6L);
        seguimiento.setFechaResolucion(LocalDate.now());
        seguimiento.setHoraResolucion(LocalTime.now());

        if (request.getRespuestaSeguimientoMemorandum() != null) {
            seguimiento.setRespuestaSeguimientoMemorandum(
                    request.getRespuestaSeguimientoMemorandum());
        }

        port.actualizar(seguimiento);
    }
}
