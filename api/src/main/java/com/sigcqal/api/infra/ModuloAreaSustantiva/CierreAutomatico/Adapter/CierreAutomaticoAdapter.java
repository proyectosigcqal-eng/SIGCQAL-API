package com.sigcqal.api.infra.ModuloAreaSustantiva.CierreAutomatico.Adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.CierreAutomatico.Model.CierreAutomatico;
import com.sigcqal.api.domain.ModuloAreaSustantiva.CierreAutomatico.Port.CierreAutomaticoPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.CierreAutomatico.Repository.CierreAutomaticoJpaRepository;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CierreAutomaticoAdapter implements CierreAutomaticoPort {

    private final CierreAutomaticoJpaRepository repository;

    @Override
    public List<CierreAutomatico> findExpedientesEnPrevencion() {
        return repository.findExpedientesEnPrevencionRaw()
                .stream()
                .map(row -> CierreAutomatico.builder()
                        .idExpediente(((Number) row[0]).intValue())
                        .folioGobierno(row[1] != null ? row[1].toString() : "")
                        .fechaSolicitud(toLocalDate(row[2]))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void cerrarExpedienteNoPresentado(Integer idExpediente) {
        repository.cerrarExpediente(idExpediente, LocalDateTime.now());
    }

    private LocalDate toLocalDate(Object raw) {
        if (raw == null) return LocalDate.now();
        if (raw instanceof java.sql.Date d) return d.toLocalDate();
        if (raw instanceof java.sql.Timestamp ts) return ts.toLocalDateTime().toLocalDate();
        return LocalDate.parse(raw.toString().substring(0, 10));
    }
}