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
        entity.setIdCorrespondencia(idCorrespondencia);
        entity.setIdUsuario(idUsuario);
        entity.setFolioUnico(folioUnico);
        entity.setAccion("REGISTRO_CORRESPONDENCIA_ENTRADA");
        entity.setFechaRegistro(LocalDateTime.now());
        jpaRepository.save(entity);
    }
}
