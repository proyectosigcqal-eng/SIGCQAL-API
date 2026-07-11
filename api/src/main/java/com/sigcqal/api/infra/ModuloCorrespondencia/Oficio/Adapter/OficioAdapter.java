package com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.Oficio.Model.Oficio;
import com.sigcqal.api.domain.ModuloCorrespondencia.Oficio.Port.OficioRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Entity.OficioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Mapper.OficioMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Repository.OficioJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class OficioAdapter implements OficioRepositoryPort { 
    
    
    private final OficioJpaRepository jpaRepository;
    private final OficioMapper mapper;

    @Override 
    public Oficio save(Oficio oficio) {
        var entity = mapper.toEntity(oficio);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existeFolio(String folio) {
       
        return jpaRepository.findByFolioUnico(folio).isPresent();
    }

    @Override
    public List<Oficio> findAll() {
    List<OficioEntity> entities = jpaRepository.findAllConRelaciones();
    return entities.stream()
                   .map(mapper::toDomain)
                   .collect(Collectors.toList());
}

@Override
public List<Oficio> findByArea(Long idArea) {
    List<OficioEntity> entities = jpaRepository.findByAreaIdConRelaciones(idArea);
    return entities.stream()
                   .map(mapper::toDomain)
                   .collect(Collectors.toList());
}
@Override
public Optional<Oficio> buscarPorId(Long id) {
    return jpaRepository.findByIdWithRelations(id)
                        .map(mapper::toDomain);
}
@Override
public List<Oficio> findSinAcuseByArea(Long idArea) {
    List<OficioEntity> entities = jpaRepository.findOficiosSinAcusePorArea(idArea);
    return entities.stream()
                   .map(mapper::toDomain)
                   .collect(Collectors.toList());
}

@Override
public List<Oficio> findAsignadosActivosPorArea(Long idArea) {
    return jpaRepository.findAsignadosActivosPorArea(idArea)
        .stream()
        .map(mapper::toDomain)
        .collect(Collectors.toList());
}

@Override
public List<Oficio> findTodosAsignadosActivos() {
    return jpaRepository.findTodosAsignadosActivos()
        .stream().map(mapper::toDomain).collect(Collectors.toList());
}

@Override
public List<Oficio> findTodosSinAcuse() {
    return jpaRepository.findTodosSinAcuse()
        .stream().map(mapper::toDomain).collect(Collectors.toList());
}
}
