package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Mapper;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Model.NotificacionCierreyAcuerdodeRazon;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Entity.NotificacionCierreyAcuerdodeRazonEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import org.springframework.stereotype.Component;


@Component
public class NotificacionCierreyAcuerdodeRazonMapper {

    // Convertir de Entidad (BD) a Modelo de Dominio (Lógica)
    public NotificacionCierreyAcuerdodeRazon toDomain(NotificacionCierreyAcuerdodeRazonEntity entity) {
        if (entity == null) return null;

        return NotificacionCierreyAcuerdodeRazon.builder()
                .idCierre(entity.getIdCierre())
                .idExpediente(entity.getExpediente().getId().intValue()) // Extraemos el ID del objeto
                .medioNotificacion(entity.getMedioNotificacion())
                .rutaArchivoAcuerdo(entity.getRutaArchivoAcuerdo())
                .fechaCierre(entity.getFechaCierre())
                .idUsuarioCierre(entity.getUsuarioCierre().getId().intValue()) // Extraemos el ID del objeto
                .build();
    }

    /**
     * Convierte del Modelo de Dominio a la Entidad de Base de Datos.
     * Recibe las entidades padre (Expediente y Usuario) ya obtenidas de la BD
     * para mantener la integridad referencial.
     */
    public NotificacionCierreyAcuerdodeRazonEntity toEntity(NotificacionCierreyAcuerdodeRazon domain, 
                                                           ExpedienteEntity expEntity, 
                                                           UsuarioEntity userEntity) {
        if (domain == null) return null;

        return NotificacionCierreyAcuerdodeRazonEntity.builder()
                .idCierre(domain.getIdCierre())
                .expediente(expEntity)
                .medioNotificacion(domain.getMedioNotificacion())
                .rutaArchivoAcuerdo(domain.getRutaArchivoAcuerdo())
                .fechaCierre(domain.getFechaCierre())
                .usuarioCierre(userEntity)
                .build();
    }

}
