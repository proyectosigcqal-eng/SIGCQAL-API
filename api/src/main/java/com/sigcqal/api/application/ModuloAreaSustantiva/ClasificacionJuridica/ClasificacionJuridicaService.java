package com.sigcqal.api.application.ModuloAreaSustantiva.ClasificacionJuridica;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ClasificacionJuridica.Model.ClasificacionJuridica;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ClasificacionJuridica.Port.ClasificacionJuridicaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port.ExpedienteRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Mapper.ClasificacionJuridicaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.ClasificacionJuridica.DTO.ClasificacionJuridicaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ClasificacionJuridica.DTO.ClasificacionJuridicaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClasificacionJuridicaService {

    private final ClasificacionJuridicaRepositoryPort port;
    private final ClasificacionJuridicaMapper mapper;
    private final ExpedienteRepositoryPort expedientePort; // ← agrega esta dependencia

    @Transactional
    public ClasificacionJuridicaResponseDTO guardar(ClasificacionJuridicaRequestDTO request) {

        // ✅ Resuelve el idExpediente numérico real desde el folio
        Integer idExpedienteReal = resolverIdExpediente(request.getIdExpediente(), request.getFolioGobierno());

        ClasificacionJuridica clasificacion = ClasificacionJuridica.builder()
                .idExpediente(idExpedienteReal)
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

    // ✅ Resuelve el id numérico real — intenta por id directo, luego por folio
    private Integer resolverIdExpediente(Integer idExpediente, String folioGobierno) {

        // Si ya tiene un id numérico válido que existe en la BD, úsalo directo
        if (idExpediente != null && idExpediente > 0) {
            boolean existe = expedientePort.existsById(idExpediente.longValue());
            if (existe) return idExpediente;
        }

        // Si no, busca por folio
        String folio = folioGobierno != null
                ? folioGobierno
                : (idExpediente != null ? String.valueOf(idExpediente) : null);

        if (folio == null || folio.isBlank()) {
            throw new InvalidRequestException("Se requiere idExpediente o folioGobierno para clasificar.");
        }

        return expedientePort.findByFolio(folio)
                .stream()
                .findFirst()
                .map(exp -> exp.getId() != null ? exp.getId().intValue() : null)
                .orElseThrow(() -> new InvalidRequestException(
                        "No se encontró expediente con folio: " + folio));
    }

    public List<ClasificacionJuridicaResponseDTO> buscarPorFolio(Integer idExpediente) {
        List<ClasificacionJuridica> resultado = port.findByFolio(idExpediente);

        if (resultado.isEmpty()) {
            throw new RuntimeException("Clasificación no encontrada para expediente: " + idExpediente);
        }

        return resultado.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}