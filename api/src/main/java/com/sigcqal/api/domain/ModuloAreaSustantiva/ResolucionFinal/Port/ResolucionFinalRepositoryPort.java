package com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalDatosPreviosDTO;

public interface ResolucionFinalRepositoryPort {

    ResolucionFinal save(ResolucionFinal resolucionFinal);

    Optional<ResolucionFinal> findById(Integer idResolucionFinal);

    List<ResolucionFinal> findAll();

    List<ResolucionFinal> findByIdExpediente(Integer idExpediente);

    boolean existsByIdExpediente(Integer idExpediente);

    /**
     * Actualiza únicamente los datos de bitácora del oficio generado
     * (ruta_resolucion_final y fecha_emision), sin tocar el resto de
     * los campos de la resolución.
     */
    ResolucionFinal actualizarOficioGenerado(
            Integer idResolucionFinal, String rutaResolucionFinal, LocalDateTime fechaEmision);
    
    Optional<ResolucionFinalDatosPreviosDTO> obtenerDatosPreviosPorFolio(String folio);
}