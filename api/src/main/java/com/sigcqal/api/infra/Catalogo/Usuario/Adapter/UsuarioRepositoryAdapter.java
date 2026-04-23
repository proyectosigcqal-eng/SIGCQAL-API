package com.sigcqal.api.infra.Catalogo.Usuario.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Usuario.Model.Usuario;
import com.sigcqal.api.domain.Catalogo.Usuario.Port.UsuarioRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Usuario.Mapper.UsuarioMapper;
import com.sigcqal.api.infra.Catalogo.Usuario.Repository.UsuarioJpaRepository;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {
    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository, UsuarioMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
