package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaAsesoria.Adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Port.BandejaAsesoriaPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Model.TramiteBandeja;
import com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaAsesoria.Repository.BandejaAsesoriaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaAsesoria.Mapper.BandejaAsesoriaMapper;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BandejaAsesoriaAdapter implements BandejaAsesoriaPort {

    private final BandejaAsesoriaRepository repository;
    private final BandejaAsesoriaMapper mapper;

    @Override
    public List<TramiteBandeja> obtenerBandeja(String search, String estatus, String tipoTramite) {
        List<Object[]> rows = repository.obtenerBandejaRaw(search, estatus, tipoTramite);
        if (rows == null) {
            return List.of();
        }
        return rows.stream()
            .filter(row -> row != null)
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
