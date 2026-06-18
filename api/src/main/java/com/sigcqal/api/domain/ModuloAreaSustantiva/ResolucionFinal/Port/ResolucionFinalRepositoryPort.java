package com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port;
 
import java.util.List;
import java.util.Optional;
 
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;
 
public interface ResolucionFinalRepositoryPort {
 
    ResolucionFinal save(ResolucionFinal resolucionFinal);
 
    Optional<ResolucionFinal> findById(Integer idResolucionFinal);
 
    Optional<ResolucionFinalEntity> findEntityById(Integer idResolucionFinal);
 
    List<ResolucionFinal> findAll();
 
    List<ResolucionFinal> findByIdExpediente(Integer idExpediente);
 
    boolean existsByIdExpediente(Integer idExpediente);
}
 