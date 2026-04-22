package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Adapter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.NotificacionPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.NotificacionEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository.NotificacionJpaRepository;

@Component
public class NotificacionAdapter implements NotificacionPort {
    private final NotificacionJpaRepository notificacionJpaRepository;

    public NotificacionAdapter(NotificacionJpaRepository notificacionJpaRepository) {
        this.notificacionJpaRepository = notificacionJpaRepository;
    }

    @Override
    public void enviarNotificacionAArea(Long idUsuarioDestino, String asunto, String mensaje, Long idFolio) {
        NotificacionEntity entity = new NotificacionEntity();
        entity.setIdUsuarioDestino(idUsuarioDestino);
        entity.setMensaje(buildMensaje(asunto, mensaje));
        entity.setIdReferencia(idFolio);
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setLeida(Boolean.FALSE);
        notificacionJpaRepository.save(entity);
    }

    private String buildMensaje(String asunto, String mensaje) {
        String a = asunto == null ? "" : asunto;
        String m = mensaje == null ? "" : mensaje;
        if (a.isBlank()) return m;
        if (m.isBlank()) return a;
        return a + "\n" + m;
    }
}

