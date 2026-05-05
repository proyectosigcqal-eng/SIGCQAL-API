package com.sigcqal.api.application.ModuloCorrespondencia.SeguimientoCorrespondencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Model.SeguimientoCorrespondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Port.ISeguimientoCorrespondenciaPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Mapper.SeguimientoCorrespondenciaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Dto.SeguimientoCorrespondenciaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Dto.SeguimientoCorrespondenciaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeguimientoCorrespondenciaService  {

    @Autowired
    private ISeguimientoCorrespondenciaPort port;

    @Autowired
    private SeguimientoCorrespondenciaMapper mapper;

    public SeguimientoCorrespondenciaResponseDTO guardar(SeguimientoCorrespondenciaRequestDTO request) {
        SeguimientoCorrespondencia seguimientoCorrespondencia = new SeguimientoCorrespondencia();

        // Mapeo de IDs y textos
        seguimientoCorrespondencia.setIdSeguimientoCorrespondencia(request.getIdSeguimientoCorrespondencia());
        seguimientoCorrespondencia.setIdCorrespondencia(request.getIdCorrespondencia());
        seguimientoCorrespondencia.setFolioRespuesta(request.getFolioRespuesta());
        seguimientoCorrespondencia.setRespuestaSeguimientoCorrespondencia(request.getRespuestaSeguimientoCorrespondencia());
        seguimientoCorrespondencia.setIdUsuario(request.getIdUsuario());
        seguimientoCorrespondencia.setIdEstatus(request.getIdEstatus());

        // --- AJUSTE PARA EL ARCHIVO ---
        // Extraemos el nombre del archivo del MultipartFile para guardarlo en la BD como String
        if (request.getArchivoAdjunto() != null && !request.getArchivoAdjunto().isEmpty()) {
            String nombreArchivo = request.getArchivoAdjunto().getOriginalFilename();
            seguimientoCorrespondencia.setArchivoAdjunto(nombreArchivo);
            
            // NOTA: Si deseas guardar el archivo físico en el servidor, 
            // aquí deberías usar request.getArchivoAdjunto().transferTo(dest);
        }

        // Manejo de fechas y horas
        if (request.getFechaResolucion() != null && !request.getFechaResolucion().isBlank()) {
            seguimientoCorrespondencia.setFechaResolucion(LocalDate.parse(request.getFechaResolucion()));
        }
        if (request.getHoraResolucion() != null && !request.getHoraResolucion().isBlank()) {
            seguimientoCorrespondencia.setHoraResolucion(LocalTime.parse(request.getHoraResolucion()));
        }

        seguimientoCorrespondencia.setFechaRegistro(LocalDateTime.now());

        var saved = port.guardar(seguimientoCorrespondencia);
        return mapper.toResponse(saved);
    }

    public List<SeguimientoCorrespondenciaResponseDTO> listarTodos() {
        return port.listarTodos()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SeguimientoCorrespondenciaResponseDTO> listarPorCorrespondenciaId(Integer idCorrespondencia) {
        return port.listarPorCorrespondenciaId(idCorrespondencia)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
