package com.sigcqal.api.infra.Catalogo.Personal.Adapter;

import com.sigcqal.api.domain.Catalogo.Personal.Model.Personal;
import com.sigcqal.api.domain.Catalogo.Personal.Port.PersonalRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Personal.Entity.PersonalEntity;
import com.sigcqal.api.infra.Catalogo.Personal.Mapper.PersonalMapper;
import com.sigcqal.api.infra.Catalogo.Personal.Repository.PersonalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalAdapter implements PersonalRepositoryPort {

    private final PersonalJpaRepository personalJpaRepository;
    private final PersonalMapper personalMapper;

    @Override
    public Personal save(Personal personal) {
        PersonalEntity entity = personalMapper.toEntity(personal);
        PersonalEntity savedEntity = personalJpaRepository.save(entity);
        return personalMapper.toDomain(savedEntity);
    }

    @Override
    public List<Personal> findAll() {
        return personalJpaRepository.findAll().stream()
                .map(personalMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Personal> findById(Long id) {
        return personalJpaRepository.findById(id)
                .map(personalMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        personalJpaRepository.deleteById(id);
    }
}