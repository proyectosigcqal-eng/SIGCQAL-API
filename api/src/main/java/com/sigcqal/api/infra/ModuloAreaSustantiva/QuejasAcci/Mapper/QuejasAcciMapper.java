package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Mapper;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Model.QuejasAcci;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Entity.QuejasAcciEntity;
import org.springframework.stereotype.Component;

@Component
public class QuejasAcciMapper {

    public QuejasAcciEntity toEntity(QuejasAcci domain) {
        QuejasAcciEntity e = new QuejasAcciEntity();
        e.setId(domain.getId());
        e.setIdQueja(domain.getIdQueja());
        e.setIdOficioAutoridad(domain.getIdOficioAutoridad());
        e.setJustificacionInvestigacion(domain.getJustificacionInvestigacion());
        e.setNuevosRequerimientosAutoridad(domain.getNuevosRequerimientosAutoridad());
        e.setPlazoDiasHabiles(domain.getPlazoDiasHabiles());
        e.setFechaEmisionAcci(domain.getFechaEmisionAcci());
        e.setRutaPdfAcci(domain.getRutaPdfAcci());
        e.setConcluido(domain.getConcluido());
        e.setFechaConclusion(domain.getFechaConclusion());
        return e;
    }

    public QuejasAcci toDomain(QuejasAcciEntity e) {
        return QuejasAcci.builder()
                .id(e.getId())
                .idQueja(e.getIdQueja())
                .idOficioAutoridad(e.getIdOficioAutoridad())
                .justificacionInvestigacion(e.getJustificacionInvestigacion())
                .nuevosRequerimientosAutoridad(e.getNuevosRequerimientosAutoridad())
                .plazoDiasHabiles(e.getPlazoDiasHabiles())
                .fechaEmisionAcci(e.getFechaEmisionAcci())
                .rutaPdfAcci(e.getRutaPdfAcci())
                .concluido(e.getConcluido())
                .fechaConclusion(e.getFechaConclusion())
                .build();
    }
}