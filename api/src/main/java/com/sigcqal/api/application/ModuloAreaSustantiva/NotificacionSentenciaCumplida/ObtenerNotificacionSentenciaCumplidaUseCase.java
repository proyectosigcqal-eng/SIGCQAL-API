package com.sigcqal.api.application.ModuloAreaSustantiva.NotificacionSentenciaCumplida;

import java.util.List;

import com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Dto.NotificacionSentenciaCumplidaResponseDTO;

public interface ObtenerNotificacionSentenciaCumplidaUseCase {

    List<NotificacionSentenciaCumplidaResponseDTO> listarTodos();
}
