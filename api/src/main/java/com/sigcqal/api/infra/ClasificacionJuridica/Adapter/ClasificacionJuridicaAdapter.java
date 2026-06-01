package com.sigcqal.api.infra.ClasificacionJuridica.Adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ClasificacionJuridica.Model.ClasificacionJuridica;
import com.sigcqal.api.domain.ClasificacionJuridica.Port.ClasificacionJuridicaRepositoryPort;
import com.sigcqal.api.infra.ClasificacionJuridica.Entity.ClasificacionJuridicaEntity;
import com.sigcqal.api.infra.ClasificacionJuridica.Mapper.ClasificacionJuridicaMapper;
import com.sigcqal.api.infra.ClasificacionJuridica.Repository.ClasifiacionJuridicaJPARepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClasificacionJuridicaAdapter implements ClasificacionJuridicaRepositoryPort{
    private final ClasifiacionJuridicaJPARepository repository;
    private final ClasificacionJuridicaMapper mapper;
    @Override
    public ClasificacionJuridica saveClasification(ClasificacionJuridica clasificacion) {
        ClasificacionJuridicaEntity entity = mapper.toEntity(clasificacion);
        ClasificacionJuridicaEntity saveEntity = repository.save(entity);
        return mapper.toDomain(saveEntity);
    }
    @Override
    public List<ClasificacionJuridica> findByFolio(Integer idExpediente){
        /*List <ClasificacionJuridicaEntity> entities = repository.findAllByIdExpediente(idExpediente);*/
        return repository.findAll()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
