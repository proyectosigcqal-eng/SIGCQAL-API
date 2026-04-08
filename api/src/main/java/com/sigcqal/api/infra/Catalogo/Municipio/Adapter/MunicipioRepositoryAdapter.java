package com.sigcqal.api.infra.Catalogo.Municipio.Adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Estado.Model.Estado;
import com.sigcqal.api.domain.Catalogo.Municipio.Model.Municipio;
import com.sigcqal.api.domain.Catalogo.Municipio.Port.MunicipioRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Municipio.Entity.MunicipioEntity;
import com.sigcqal.api.infra.Catalogo.Municipio.Repository.MunicipioJpaRepository;

@Component
public class MunicipioRepositoryAdapter implements MunicipioRepositoryPort {

    private final MunicipioJpaRepository jpaRepository;

    public MunicipioRepositoryAdapter(MunicipioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Municipio> findByName(String nombre) {
        return jpaRepository.findByNombreMunicipio(nombre)
                            .map(this::mapToDomain);
    }

    @Override
    public List<Municipio> ListAll() {
        return jpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

   private Municipio mapToDomain(MunicipioEntity e) {
    Municipio dom = new Municipio(); 
    dom.setId(e.getId());
    dom.setNombreMunicipio(e.getNombreMunicipio());

    if (e.getEstado() != null) {
        Estado estadoDom = new Estado();
        estadoDom.setIdEstado(e.getEstado().getIdEstado()); 
        estadoDom.setNombreEstado(e.getEstado().getNombreEstado()); 
        
        dom.setEstado(estadoDom); 
    }

    return dom; 
}
}