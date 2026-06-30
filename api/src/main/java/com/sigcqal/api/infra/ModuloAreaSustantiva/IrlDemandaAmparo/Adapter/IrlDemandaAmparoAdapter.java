package com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Adapter;

import com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Model.IrlDemandaAmparo;
import com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Port.IrlDemandaAmparoRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Mapper.IrlDemandaAmparoMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Repository.DatosQuejoso;
import com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Repository.IrlDemandaAmparoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IrlDemandaAmparoAdapter implements IrlDemandaAmparoRepositoryPort {

    private final IrlDemandaAmparoJpaRepository repository;
    private final IrlDemandaAmparoMapper mapper;

    @Override
    public IrlDemandaAmparo save(IrlDemandaAmparo domain) {
        return mapper.toDomain(repository.save(mapper.toEntity(domain)));
    }

    @Override
    public Optional<IrlDemandaAmparo> findById(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
public Optional<IrlDemandaAmparo> findByIdEnriquecido(Integer id) {
    return repository.findById(id).map(entity -> {
        IrlDemandaAmparo dominio = mapper.toDomain(entity);
        List<Object[]> rows = repository.findDatosQuejosoRaw(entity.getIdExpediente());
        if (rows != null && !rows.isEmpty()) {
            DatosQuejoso datos = DatosQuejoso.from(rows.get(0));
            dominio.setNombreQuejoso(datos.getNombreQuejoso());
            dominio.setCalleQuejoso(datos.getCalleQuejoso());
            dominio.setNumCalleQuejoso(datos.getNumCalleQuejoso());
            dominio.setColoniaQuejoso(datos.getColoniaQuejoso());
            dominio.setCpQuejoso(datos.getCpQuejoso());
        }
        return dominio;
    });
}
    @Override
    public Optional<IrlDemandaAmparo> findByIdExpediente(Integer idExpediente) {
        return repository.findByIdExpediente(idExpediente).map(mapper::toDomain);
    }

    @Override
    public Optional<IrlDemandaAmparo> findByIdRepresentacionLegal(Integer idRepresentacionLegal) {
        return repository.findByIdRepresentacionLegal(idRepresentacionLegal).map(mapper::toDomain);
    }

    @Override
    public List<IrlDemandaAmparo> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByIdExpediente(Integer idExpediente) {
        return repository.existsByIdExpediente(idExpediente);
    }
}