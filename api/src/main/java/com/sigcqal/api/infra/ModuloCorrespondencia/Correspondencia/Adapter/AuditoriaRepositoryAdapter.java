package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Adapter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port.AuditoriaPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.AuditoriaCorrespondenciaEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Repository.AuditoriaCorrespondenciaJpaRepository;

@Component
public class AuditoriaRepositoryAdapter implements AuditoriaPort {
    private final AuditoriaCorrespondenciaJpaRepository jpaRepository;

    public AuditoriaRepositoryAdapter(AuditoriaCorrespondenciaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void registrarRegistroCorrespondencia(Long idCorrespondencia, Long idUsuario, String folioUnico) {
        AuditoriaCorrespondenciaEntity entity = new AuditoriaCorrespondenciaEntity();
        entity.setIdFolio(idCorrespondencia);
        entity.setIdUsuarioAccion(idUsuario);
        entity.setIdEstadoAnterior(null);
        entity.setIdEstadoNuevo(1L);
        entity.setObservaciones("REGISTRO_CORRESPONDENCIA_ENTRADA folio=" + folioUnico);
        entity.setFechaMovimiento(LocalDateTime.now());
        jpaRepository.save(entity);
    }
}
