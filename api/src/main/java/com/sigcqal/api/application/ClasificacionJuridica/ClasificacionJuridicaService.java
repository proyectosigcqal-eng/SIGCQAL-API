package com.sigcqal.api.application.ClasificacionJuridica;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ClasificacionJuridica.Model.ClasificacionJuridica;
import com.sigcqal.api.domain.ClasificacionJuridica.Port.ClasificacionJuridicaRepositoryPort;
import com.sigcqal.api.infra.ClasificacionJuridica.Mapper.ClasificacionJuridicaMapper;
import com.sigcqal.api.web.ClasificacionJuridica.DTO.ClasificacionJuridicaRequestDTO;
import com.sigcqal.api.web.ClasificacionJuridica.DTO.ClasificacionJuridicaResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClasificacionJuridicaService {
    private final ClasificacionJuridicaRepositoryPort port;
    private final ClasificacionJuridicaMapper mapper;
    
    @Transactional
    public ClasificacionJuridicaResponseDTO guardar(ClasificacionJuridicaRequestDTO request){
        ClasificacionJuridica clasificacion = ClasificacionJuridica.builder()
        .idExpediente(request.getIdExpediente())
        .tipoActo(request.getTipoActo())
        .idAutoridad(request.getIdAutoridad())
        .idEstatusDetalleExpediente(request.getIdEstatusDetalleExpediente())
        .idTipoEntrada(request.getIdTipoEntrada())
        .calificacionActo(request.getCalificacionActo())
        .problematica(request.getProblematica())
        .seguimientoAsesoria(request.getSeguimientoAsesoria())
        .monto(request.getMonto())
        .fechaNotificacion(request.getFechaNotificacion())
        .nombreAutoridad(request.getNombreAutoridad())
        .nombreTipoActo(request.getNombreTipoActo())
        .nombreEstatusDetalle(request.getNombreEstatusDetalle())
        .nombreTipoEntrada(request.getNombreTipoEntrada())
        .build();

        return mapper.toResponse(port.saveClasification(clasificacion));
    }

    public List<ClasificacionJuridicaResponseDTO> buscarPorFolio(Integer idExpediente){
        List<ClasificacionJuridica> resultado = port.findByFolio(idExpediente);
        
        if (resultado.isEmpty()) {                                                          // 👈
            throw new RuntimeException("Clasificación no encontrada para expediente: " + idExpediente);
        }

        return resultado.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
    
}
