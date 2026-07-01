package com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Port;

import com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Model.IrlDemandaAmparo;
import java.util.List;
import java.util.Optional;

public interface IrlDemandaAmparoRepositoryPort {
    IrlDemandaAmparo save(IrlDemandaAmparo domain);
    Optional<IrlDemandaAmparo> findById(Integer id);
    Optional<IrlDemandaAmparo> findByIdExpediente(Integer idExpediente);
    Optional<IrlDemandaAmparo> findByIdRepresentacionLegal(Integer idRepresentacionLegal);
    List<IrlDemandaAmparo> findAll();
    boolean existsByIdExpediente(Integer idExpediente);
    Optional<IrlDemandaAmparo> findByIdEnriquecido(Integer id);
}