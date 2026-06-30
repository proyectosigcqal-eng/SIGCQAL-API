package com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Model.NotificacionSentenciaCumplida;

public interface NotificacionSentenciaCumplidaRepositoryPort {

    NotificacionSentenciaCumplida save(NotificacionSentenciaCumplida notificacionSentenciaCumplida);

    Optional<NotificacionSentenciaCumplida> findById(Integer idSentenciaCumplida);

    List<NotificacionSentenciaCumplida> findAll();

    boolean existsById(Integer idSentenciaCumplida);
}
