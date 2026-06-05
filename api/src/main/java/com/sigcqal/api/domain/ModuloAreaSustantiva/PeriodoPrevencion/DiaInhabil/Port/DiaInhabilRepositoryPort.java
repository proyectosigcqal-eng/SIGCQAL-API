package com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port;

import java.time.LocalDate;
import java.util.List;

import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Model.DiaInhabil;

public interface DiaInhabilRepositoryPort {
    List<DiaInhabil> findByRangoFechas(LocalDate desde, LocalDate hasta);
}
