package com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Mapper;

import com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Model.IrlDemandaAmparo;
import com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Entity.IrlDemandaAmparoEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Repository.DatosQuejoso;
import org.springframework.stereotype.Component;

@Component
public class IrlDemandaAmparoMapper {

    public IrlDemandaAmparoEntity toEntity(IrlDemandaAmparo d) {
        return IrlDemandaAmparoEntity.builder()
                .idDemandaAmparo(d.getIdDemandaAmparo())
                .idExpediente(d.getIdExpediente())
                .idRepresentacionLegal(d.getIdRepresentacionLegal())
                .idRlCir(d.getIdRlCir())
                .idQuejaRlCir(d.getIdQuejaRlCir())
                .autoridadReclamadaMunicipio(d.getAutoridadReclamadaMunicipio())
                .superficieTerreno(d.getSuperficieTerreno())
                .superficieConstruccion(d.getSuperficieConstruccion())
                .tipoConstruccion(d.getTipoConstruccion())
                .zonificacion(d.getZonificacion())
                .folioReciboPago(d.getFolioReciboPago())
                .montoPago(d.getMontoPago())
                .fechaPrimerPago(d.getFechaPrimerPago())
                .incluyeMultasHistoricas(d.getIncluyeMultasHistoricas())
                .aniosMultasHistoricas(d.getAniosMultasHistoricas())
                .argumentacionFaltaNotificacion(d.getArgumentacionFaltaNotificacion())
                .transcripcionLeyIngresos(d.getTranscripcionLeyIngresos())
                .rutaPdfDemandaGenerada(d.getRutaPdfDemandaGenerada())
                .fechaGeneracionDemanda(d.getFechaGeneracionDemanda())
                .rutaPdfDemandaPresentada(d.getRutaPdfDemandaPresentada())
                .rutaPdfAcuseDemanda(d.getRutaPdfAcuseDemanda())
                .fechaPresentacionDemanda(d.getFechaPresentacionDemanda())
                .fechaRegistro(d.getFechaRegistro())
                .ultimaActualizacion(d.getUltimaActualizacion())
                .clavePredial(d.getClavePredial())
                .numCuenta(d.getNumCuenta())
                .numRecibo1(d.getNumRecibo1())
                .numRecibo2(d.getNumRecibo2())
                .folioReciboPago2(d.getFolioReciboPago2())
                .domicilioAutoridad(d.getDomicilioAutoridad())
                .build();
    }

    public IrlDemandaAmparo toDomain(IrlDemandaAmparoEntity e) {
        return IrlDemandaAmparo.builder()
                .idDemandaAmparo(e.getIdDemandaAmparo())
                .idExpediente(e.getIdExpediente())
                .idRepresentacionLegal(e.getIdRepresentacionLegal())
                .idRlCir(e.getIdRlCir())
                .idQuejaRlCir(e.getIdQuejaRlCir())
                .autoridadReclamadaMunicipio(e.getAutoridadReclamadaMunicipio())
                .superficieTerreno(e.getSuperficieTerreno())
                .superficieConstruccion(e.getSuperficieConstruccion())
                .tipoConstruccion(e.getTipoConstruccion())
                .zonificacion(e.getZonificacion())
                .folioReciboPago(e.getFolioReciboPago())
                .montoPago(e.getMontoPago())
                .fechaPrimerPago(e.getFechaPrimerPago())
                .incluyeMultasHistoricas(e.getIncluyeMultasHistoricas())
                .aniosMultasHistoricas(e.getAniosMultasHistoricas())
                .argumentacionFaltaNotificacion(e.getArgumentacionFaltaNotificacion())
                .transcripcionLeyIngresos(e.getTranscripcionLeyIngresos())
                .rutaPdfDemandaGenerada(e.getRutaPdfDemandaGenerada())
                .fechaGeneracionDemanda(e.getFechaGeneracionDemanda())
                .rutaPdfDemandaPresentada(e.getRutaPdfDemandaPresentada())
                .rutaPdfAcuseDemanda(e.getRutaPdfAcuseDemanda())
                .fechaPresentacionDemanda(e.getFechaPresentacionDemanda())
                .fechaRegistro(e.getFechaRegistro())
                .ultimaActualizacion(e.getUltimaActualizacion())
                .clavePredial(e.getClavePredial())
                .numCuenta(e.getNumCuenta())
                .numRecibo1(e.getNumRecibo1())
                .numRecibo2(e.getNumRecibo2())
                .folioReciboPago2(e.getFolioReciboPago2())
                .domicilioAutoridad(e.getDomicilioAutoridad())
                // nombreQuejoso, calleQuejoso, coloniaQuejoso se inyectan después via enriquecer()
                .build();
    }

    // ── Enriquece un dominio ya construido con los datos del quejoso ──
    public IrlDemandaAmparo enriquecer(IrlDemandaAmparo dominio, DatosQuejoso datos) {
        return dominio.toBuilder()
                .nombreQuejoso(datos.getNombreQuejoso())
                .calleQuejoso(datos.getCalleQuejoso())
                .coloniaQuejoso(datos.getColoniaQuejoso())
                .cpQuejoso(datos.getCpQuejoso())
                .numCalleQuejoso(datos.getNumCalleQuejoso())
                .build();
    }
}