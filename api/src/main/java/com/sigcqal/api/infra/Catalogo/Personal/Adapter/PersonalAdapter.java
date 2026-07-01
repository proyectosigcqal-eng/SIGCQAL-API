package com.sigcqal.api.infra.Catalogo.Personal.Adapter;

import com.sigcqal.api.domain.Catalogo.Direccion.Model.Direccion;
import com.sigcqal.api.domain.Catalogo.Personal.Model.Personal;
import com.sigcqal.api.domain.Catalogo.Personal.Port.PersonalRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Direccion.Entity.DireccionEntity;
import com.sigcqal.api.infra.Catalogo.Direccion.Repository.DireccionJpaRepository;
import com.sigcqal.api.infra.Catalogo.Personal.Entity.PersonalEntity;
import com.sigcqal.api.infra.Catalogo.Personal.Mapper.PersonalMapper;
import com.sigcqal.api.infra.Catalogo.Personal.Repository.PersonalJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalAdapter implements PersonalRepositoryPort {

    private final PersonalJpaRepository personalJpaRepository;
    private final PersonalMapper personalMapper;
    private final DireccionJpaRepository direccionRepository;

    @Override
    public Personal save(Personal personalDominio) {
        PersonalEntity entity = personalMapper.toEntity(personalDominio);
        PersonalEntity savedEntity = personalJpaRepository.save(entity);
        return personalMapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public Personal save(Personal personalDominio, Direccion direccionDominio) {
        
        PersonalEntity entity = personalMapper.toEntity(personalDominio);
        
        // Recuperar la entidad real desde BD para que JPA sepa que existe
        DireccionEntity dirEntity;
    
        // 2. Lógica de registro o actualización
        // Solo buscamos si el ID existe (PUT). Si es null, creamos una nueva (POST).
        if (direccionDominio.getId() != null) {
            dirEntity = direccionRepository.findById(direccionDominio.getId())
                .orElse(new DireccionEntity());
        } else {
            dirEntity = new DireccionEntity();
        }  

        // Mapear manualmente los cambios a la entidad recuperada
        dirEntity.setCalle(direccionDominio.getCalle());
        dirEntity.setColonia(direccionDominio.getColonia());
        dirEntity.setCp(direccionDominio.getCp());
        dirEntity.setIdEstado(direccionDominio.getIdEstado());
        dirEntity.setIdMunicipio(direccionDominio.getIdMunicipio());
        dirEntity.setNumExt(direccionDominio.getNumExt());
        dirEntity.setNumInt(direccionDominio.getNumInt());
        // ... copiar todos los campos ...
        
        entity.getPersona().setDireccion(dirEntity);
        
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