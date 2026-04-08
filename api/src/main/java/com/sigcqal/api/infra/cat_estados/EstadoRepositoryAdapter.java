package com.sigcqal.api.infra.cat_estados;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.cat_estados.Estado;
import com.sigcqal.api.domain.cat_estados.EstadoRepositoryPort;

@Component
public class EstadoRepositoryAdapter implements EstadoRepositoryPort {

    private final EstadoJpaRepository jpaRepository;

    public EstadoRepositoryAdapter(EstadoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Estado> findByName(String nombre) {
        return jpaRepository.findByNombreEstado(nombre)
                .map(this::mapToDomain);
    }

    @Override
    public List<Estado> ListAll() {
        return jpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private Estado mapToDomain(EstadoEntity e) {
        Estado dom = new Estado();
        dom.setIdEstado(e.getIdEstado());
        dom.setNombreEstado(e.getNombreEstado());
        return dom;
    }
}
