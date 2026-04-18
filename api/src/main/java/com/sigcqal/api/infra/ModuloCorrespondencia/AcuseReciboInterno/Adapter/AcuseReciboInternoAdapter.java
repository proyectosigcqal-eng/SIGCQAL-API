package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Model.AcuseReciboInterno;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Port.AcuseReciboInternoRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Mapper.AcuseReciboInternoMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Repository.AcuseReciboInternoJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AcuseReciboInternoAdapter implements AcuseReciboInternoRepositoryPort {

    private final AcuseReciboInternoJpaRepository repository;
    private final AcuseReciboInternoMapper mapper;

    @Override
    public List<AcuseReciboInterno> findByUsuario(Long idUsuario) {
        return repository.findByUsuarioRevisor_Id(idUsuario)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AcuseReciboInterno> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
public AcuseReciboInterno save(AcuseReciboInterno acuse) {

    var entity = repository.findById(acuse.getIdAcuse())
        .orElseThrow(() -> new RuntimeException("Acuse no encontrado"));

    
    entity.setEsDelArea(acuse.getEsDelArea());
    entity.setFechaAceptacion(acuse.getFechaAceptacion());
    entity.setHoraAceptacion(acuse.getHoraAceptacion());

    var saved = repository.save(entity);

    return mapper.toDomain(saved);
}

@Override
public boolean existePorMemorandum(Long idMemorandum) {
    return repository.existsByMemorandum_Id(idMemorandum);
}
}