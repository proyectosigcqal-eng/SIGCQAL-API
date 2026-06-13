package com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ClasificacionJuridica.Model.ClasificacionJuridica;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ClasificacionJuridica.Port.ClasificacionJuridicaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Entity.ClasificacionJuridicaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Mapper.ClasificacionJuridicaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Repository.ClasifiacionJuridicaJPARepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClasificacionJuridicaAdapter implements ClasificacionJuridicaRepositoryPort{
    private final ClasifiacionJuridicaJPARepository repository;
    private final ClasificacionJuridicaMapper mapper;
    @Override
    public ClasificacionJuridica saveClasification(ClasificacionJuridica clasificacion) {
        ClasificacionJuridicaEntity entity = mapper.toEntity(clasificacion);

        Integer idExpediente = entity.getIdExpediente();
        if (idExpediente != null) {
            List<ClasificacionJuridicaEntity> existentes = repository.findAllByIdExpediente(idExpediente);
            if (!existentes.isEmpty()) {
                entity.setId(existentes.get(0).getId());
            }
        }

        ClasificacionJuridicaEntity saveEntity = repository.save(entity);
        return mapper.toDomain(saveEntity);
    }
    @Override
    public List<ClasificacionJuridica> findByFolio(Integer idExpediente){
         return repository.findAllByIdExpediente(idExpediente)
        .stream()
        .map(mapper::toDomain)
        .collect(Collectors.toList());
    }


}
