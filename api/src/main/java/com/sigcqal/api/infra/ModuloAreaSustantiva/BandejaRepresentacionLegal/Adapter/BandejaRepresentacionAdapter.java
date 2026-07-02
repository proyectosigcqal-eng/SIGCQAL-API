
package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Adapter;
 
import com.sigcqal.api.application.ModuloAreaSustantiva.IrlDemandaAmparo.SemaforoJudicialService;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Model.RepresentacionBandeja;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Port.BandejaRepresentacionPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Mapper.BandejaRepresentacionMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Repository.BandejaRepresentacionRepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto.SemaforoJudicialDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Component
@RequiredArgsConstructor
public class BandejaRepresentacionAdapter implements BandejaRepresentacionPort {
 
    private final BandejaRepresentacionRepository repository;
    private final BandejaRepresentacionMapper      mapper;
    private final SemaforoJudicialService          semaforoJudicialService;
 
    @Override
    public List<RepresentacionBandeja> obtenerBandeja(
            Boolean esEvolucion,   // ← NUEVO
            String search,
            String estatus,
            String tipoTramite) {
 
        List<Object[]> rows = repository.obtenerBandejaRaw(
            search,
            estatus,
            tipoTramite,
            esEvolucion   // ← NUEVO — pasa al repo para filtrar en SQL
        );
 
        if (rows == null) return List.of();
 
        return rows.stream()
            .filter(row -> row != null)
            .map(mapper::toDomain)
            .map(this::enriquecerConSemaforo)
            .collect(Collectors.toList());
    }
 
    private RepresentacionBandeja enriquecerConSemaforo(RepresentacionBandeja bandeja) {
        if (bandeja.getIdDemandaAmparo() != null) {
            try {
                SemaforoJudicialDTO semaforo = semaforoJudicialService
                    .calcularSemaforoJudicial(bandeja.getIdDemandaAmparo());
                bandeja.setSemaforo(semaforo.getColor());
            } catch (Exception e) {
                bandeja.setSemaforo(null);
            }
        }
        return bandeja;
    }
}
 