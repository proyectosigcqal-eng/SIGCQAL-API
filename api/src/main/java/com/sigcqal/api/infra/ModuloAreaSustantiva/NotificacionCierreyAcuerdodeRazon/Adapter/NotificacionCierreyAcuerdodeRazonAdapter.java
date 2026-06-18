package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Adapter;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Port.NotificacionCierreyAcuerdodeRazonRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Model.NotificacionCierreyAcuerdodeRazon;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Mapper.NotificacionCierreyAcuerdodeRazonMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Repository.NotificacionCierreyAcuerdodeRazonJPARepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Repository.CierreExpedienteUpdateRepository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;
import com.sigcqal.api.infra.Catalogo.Usuario.Repository.UsuarioJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionCierreyAcuerdodeRazonAdapter implements NotificacionCierreyAcuerdodeRazonRepositoryPort {

    private final NotificacionCierreyAcuerdodeRazonJPARepository jpaRepository;
    private final NotificacionCierreyAcuerdodeRazonMapper mapper;
    private final ExpedienteJPARepository expedienteRepository;
    private final UsuarioJpaRepository usuarioRepository;
    private final CierreExpedienteUpdateRepository cierreExpedienteUpdateRepository;


@Override
    public NotificacionCierreyAcuerdodeRazon save(NotificacionCierreyAcuerdodeRazon cierre) {
        // Validamos que los IDs no sean nulos antes de buscar
        if (cierre.getIdExpediente() == null || cierre.getIdUsuarioCierre() == null) {
            throw new IllegalArgumentException("El ID del Expediente y del Usuario no pueden ser nulos");
        }
        // 1. Recuperamos las entidades
        var expEntity = expedienteRepository.findById(cierre.getIdExpediente())
                .orElseThrow(() -> new RuntimeException("Expediente no encontrado con ID: " + cierre.getIdExpediente()));
        
        cierreExpedienteUpdateRepository.actualizarEstatusExpediente(cierre.getIdExpediente());

        var userEntity = usuarioRepository.findById(cierre.getIdUsuarioCierre().longValue())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + cierre.getIdUsuarioCierre()));

        // 2. Mapeamos y persistimos
        var entity = mapper.toEntity(cierre, expEntity, userEntity);
        var entityGuardada = jpaRepository.save(entity);
        
        return mapper.toDomain(entityGuardada);
    }

    @Override
    public List<NotificacionCierreyAcuerdodeRazon> findById(Integer idExpediente) {
    // Es buena práctica convertir el Integer a long si tu JPA lo requiere, 
    // o simplemente pasar el Integer si tu repositorio usa Integer.
    return jpaRepository.findByExpediente_Id(idExpediente).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    
}
}
