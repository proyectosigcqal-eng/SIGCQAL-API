package com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port;

import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;

public interface ResolucionFinalRepositoryPort {
    ResolucionFinal guardarResolucion(ResolucionFinal resolucion);
    Optional<ResolucionFinal> buscarPorExpediente(Integer idExpediente);
    boolean expedienteEnDictaminacion(Integer idExpediente);
}
