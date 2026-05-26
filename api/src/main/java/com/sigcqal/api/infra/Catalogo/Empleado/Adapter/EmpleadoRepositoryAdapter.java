package com.sigcqal.api.infra.Catalogo.Empleado.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Empleado.Model.Empleado;
import com.sigcqal.api.domain.Catalogo.Empleado.Port.EmpleadoRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Empleado.Mapper.EmpleadoMapper;
import com.sigcqal.api.infra.Catalogo.Empleado.Repository.EmpleadoJpaRepository;

@Component
public class EmpleadoRepositoryAdapter implements EmpleadoRepositoryPort {
    private final EmpleadoJpaRepository jpaRepository;
    private final EmpleadoMapper mapper;

    public EmpleadoRepositoryAdapter(EmpleadoJpaRepository jpaRepository, EmpleadoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Empleado> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Empleado> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
