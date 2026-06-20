package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port.ResolucionFinalRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper.ResolucionFinalMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Repository.ResolucionFinalJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalDatosPreviosDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResolucionFinalAdapter implements ResolucionFinalRepositoryPort {

    private final ResolucionFinalJPARepository repository;
    private final ResolucionFinalMapper         mapper;

    @Override
    public ResolucionFinal save(ResolucionFinal resolucionFinal) {
        ResolucionFinalEntity entity = mapper.toEntity(resolucionFinal);
        ResolucionFinalEntity guardado = repository.save(entity);
        return mapper.toDomain(guardado);
    }

    @Override
    public Optional<ResolucionFinal> findById(Integer idResolucionFinal) {
        return repository.findById(idResolucionFinal)
                .map(mapper::toDomain);
    }

    @Override
    public List<ResolucionFinal> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResolucionFinal> findByIdExpediente(Integer idExpediente) {
        return repository.findByIdExpediente(idExpediente)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByIdExpediente(Integer idExpediente) {
        return repository.existsByIdExpediente(idExpediente);
    }

    @Override
    public ResolucionFinal actualizarOficioGenerado(
            Integer idResolucionFinal, String rutaResolucionFinal, LocalDateTime fechaEmision) {

        ResolucionFinalEntity entity = repository.findById(idResolucionFinal)
                .orElseThrow(() -> new RuntimeException(
                        "Resolución final no encontrada: " + idResolucionFinal));

        entity.setRutaResolucionFinal(rutaResolucionFinal);
        entity.setFechaEmision(fechaEmision);

        return mapper.toDomain(repository.save(entity));
    }

@Override
public Optional<ResolucionFinalDatosPreviosDTO> obtenerDatosPreviosPorFolio(String folio) {
    List<Object[]> rows = repository.findDatosPreviosByFolio(folio);
    if (rows.isEmpty()) return Optional.empty();

    Object[] row = rows.get(0);
    return Optional.of(ResolucionFinalDatosPreviosDTO.builder()
            .idExpediente(row[0] != null ? ((Number) row[0]).intValue() : null)
            .idAri(row[1] != null ? ((Number) row[1]).intValue() : null)
            .idQuejaRespuestaAutoridad(row[2] != null ? ((Number) row[2]).intValue() : null)
            .idEstatusQueja(row[3] != null ? ((Number) row[3]).intValue() : null)
            .idEstatusExpediente(row[4] != null ? ((Number) row[4]).intValue() : null)
            
            // ✅ CORRECCIÓN: Extraemos como Timestamp y lo convertimos a LocalDate
            .fechaSolicitud(row[5] != null ? ((java.sql.Timestamp) row[5]).toLocalDateTime().toLocalDate() : null)
            
            .numeroOficio(row[6] != null ? row[6].toString() : null)
            
            // ✅ CORRECCIÓN: Aplicamos lo mismo para la fecha del oficio por seguridad
            .fechaOficio(row[7] != null ? ((java.sql.Timestamp) row[7]).toLocalDateTime().toLocalDate() : null)
            
            .build());
}
}