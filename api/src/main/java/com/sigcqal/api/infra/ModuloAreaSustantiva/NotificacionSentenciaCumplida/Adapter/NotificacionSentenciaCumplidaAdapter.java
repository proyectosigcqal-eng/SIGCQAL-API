package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Model.NotificacionSentenciaCumplida;
import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Port.NotificacionSentenciaCumplidaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Entity.NotificacionSentenciaCumplidaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Mapper.NotificacionSentenciaCumplidaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Repository.NotificacionSentenciaCumplidaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificacionSentenciaCumplidaAdapter implements NotificacionSentenciaCumplidaRepositoryPort {

    private final NotificacionSentenciaCumplidaJpaRepository repository;
    private final NotificacionSentenciaCumplidaMapper mapper;

    @Override
    public NotificacionSentenciaCumplida save(NotificacionSentenciaCumplida notificacionSentenciaCumplida) {
        NotificacionSentenciaCumplidaEntity entity = mapper.toEntity(notificacionSentenciaCumplida);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<NotificacionSentenciaCumplida> findById(Integer idSentenciaCumplida) {
        return repository.findById(idSentenciaCumplida).map(mapper::toDomain);
    }

    @Override
    public List<NotificacionSentenciaCumplida> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Integer idSentenciaCumplida) {
        return repository.existsById(idSentenciaCumplida);
    }
}
