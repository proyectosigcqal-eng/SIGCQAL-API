package com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Port;

import java.util.List;
import java.util.Optional;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.Queja;

public interface QuejaRepositoryPort {
    Optional<Queja> findById(Integer idQueja);
    List<Queja> findAll();

    // ← NUEVOS para el checklist
    void admitir(Integer idDetalleAsesoria, Integer idExpediente);
    void requerirAclaracion(Integer idExpediente);

    void actualizarRequisitos(String folio, Boolean identificacion,
                           Boolean actosFiscales, Boolean narrativa,
                           Boolean competencia);

    Integer findIdByFolio(String folio); 
}