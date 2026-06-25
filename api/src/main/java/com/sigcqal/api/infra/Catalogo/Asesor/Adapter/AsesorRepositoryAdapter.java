package com.sigcqal.api.infra.Catalogo.Asesor.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.domain.Catalogo.Asesor.Model.Asesor;
import com.sigcqal.api.domain.Catalogo.Asesor.Port.AsesorRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
import com.sigcqal.api.infra.Catalogo.Asesor.Mapper.AsesorMapper;
import com.sigcqal.api.infra.Catalogo.Asesor.Repository.AsesorJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AsesorRepositoryAdapter implements AsesorRepositoryPort {

    private final AsesorJpaRepository jpaRepository;
    private final AsesorMapper        mapper;

    @Override
    public List<Asesor> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Asesor save(Asesor asesor) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(asesor)));
    }

    @Override
    public Optional<Asesor> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    @Transactional
    public void actualizar(Long id, Asesor asesor) {
        AsesorEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asesor no encontrado: " + id));

        if (asesor.getEspecialidad() != null) {
            entity.setEspecialidad(asesor.getEspecialidad());
        }

        jpaRepository.save(entity);
    }
@Override
@Transactional
public void darBaja(Long id) {
    AsesorEntity entity = jpaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Asesor no encontrado: " + id));
    entity.setActivo(false);  // ✅ baja lógica correcta
    jpaRepository.save(entity);
}
}