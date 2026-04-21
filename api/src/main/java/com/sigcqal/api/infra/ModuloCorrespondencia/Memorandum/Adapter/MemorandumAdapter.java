
package com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Model.Memorandum;
import com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Port.MemorandumRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Entity.MemorandumEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Mapper.MemorandumMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Repository.MemorandumJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class MemorandumAdapter implements MemorandumRepositoryPort { 
    
    
    private final MemorandumJpaRepository jpaRepository;
    private final MemorandumMapper mapper;

    @Override 
    public Memorandum save(Memorandum memorandum) {
        var entity = mapper.toEntity(memorandum);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existeFolio(String folio) {
       
        return jpaRepository.findByFolioUnico(folio).isPresent();
    }

    @Override
    public List<Memorandum> findAll() {
    List<MemorandumEntity> entities = jpaRepository.findAll();
    return entities.stream()
                   .map(mapper::toDomain)
                   .collect(Collectors.toList());
}

@Override
public List<Memorandum> findByArea(Long idArea) {
    List<MemorandumEntity> entities = jpaRepository.findByAreaId(idArea);
    return entities.stream()
                   .map(mapper::toDomain)
                   .collect(Collectors.toList());
}

@Override
public Optional<Memorandum> buscarPorId(Long id) {
    return jpaRepository.findById(id)
                        .map(mapper::toDomain);
}
}