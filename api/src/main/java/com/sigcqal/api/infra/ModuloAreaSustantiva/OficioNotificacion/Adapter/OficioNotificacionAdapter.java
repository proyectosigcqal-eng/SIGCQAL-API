package com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Adapter;

import com.sigcqal.api.domain.ModuloAreaSustantiva.OficioNotificacion.Model.OficioNotificacion;
import com.sigcqal.api.domain.ModuloAreaSustantiva.OficioNotificacion.Port.OficioNotificacionRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Mapper.OficioNotificacionMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Repository.OficioNotificacionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OficioNotificacionAdapter implements OficioNotificacionRepositoryPort {

    private final OficioNotificacionJpaRepository repo;
    private final OficioNotificacionMapper mapper;

    @Override
    public OficioNotificacion guardar(OficioNotificacion oficio) {
        return mapper.toDomain(repo.save(mapper.toEntity(oficio)));
    }

   @Override
public List<OficioNotificacion> buscarPorFolio(String folio) {
    return repo.findByFolioExpedienteOrderByFechaGeneracionDesc(folio)
               .stream()
               .map(mapper::toDomain)
               .collect(Collectors.toList());
}
}