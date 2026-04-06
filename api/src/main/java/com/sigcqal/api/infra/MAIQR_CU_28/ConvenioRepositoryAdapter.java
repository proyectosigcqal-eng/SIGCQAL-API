package com.sigcqal.api.infra.MAIQR_CU_28;

import com.sigcqal.api.domain.MAIQR_CU_28.ConvenioMunicipio;
import com.sigcqal.api.domain.MAIQR_CU_28.ConvenioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConvenioRepositoryAdapter implements ConvenioRepositoryPort {

    private final ConvenioJpaRepository jpaRepository;

    public ConvenioRepositoryAdapter(ConvenioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ConvenioMunicipio save(ConvenioMunicipio dom) {
        ConvenioEntity entity = new ConvenioEntity();
        entity.setNombreMunicipio(dom.getNombreMunicipio());
        entity.setVigente(dom.isVigente());
        ConvenioEntity guardado = jpaRepository.save(entity);
        
        return mapToDomain(guardado);
    }

    @Override
    public Optional<ConvenioMunicipio> findByName(String nombre) {
        return jpaRepository.findByNombreMunicipio(nombre)
                            .map(this::mapToDomain);
    }

    @Override
    public List<ConvenioMunicipio> ListAll() {
        return jpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private ConvenioMunicipio mapToDomain(ConvenioEntity e) {
        ConvenioMunicipio dom = new ConvenioMunicipio(); 
        dom.setId(e.getId());
        dom.setNombreMunicipio(e.getNombreMunicipio());
        dom.setVigente(e.isVigente());
        return dom; 
    }
}