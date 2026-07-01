package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Adapter;

import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Model.RepresentacionBandeja;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Port.BandejaRepresentacionPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Mapper.BandejaRepresentacionMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Repository.BandejaRepresentacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BandejaRepresentacionAdapter implements BandejaRepresentacionPort {

    private final BandejaRepresentacionRepository repository;
    private final BandejaRepresentacionMapper mapper;

    @Override
    public List<RepresentacionBandeja> obtenerBandeja(String search, String estatus, String tipoTramite) {
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
