package com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port;

public interface AuditoriaPort {
    void registrarRegistroCorrespondencia(Long idCorrespondencia, Long idUsuario, String folioUnico);
}
