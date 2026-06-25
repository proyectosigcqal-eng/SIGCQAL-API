package com.sigcqal.api.application.ModuloAreaSustantiva.ContestacionAutoridad;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Model.ContestacionAutoridad;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Port.ContestacionAutoridadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.EstatusQuejaIds;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.ContestacionAutoridad.Dto.ContestacionAutoridadResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContestacionAutoridadService {

    private final ContestacionAutoridadPort port;
    private final FileUploadPort fileUploadPort;
    private final QuejaJPARepository quejaJpaRepository;
   

    @Transactional
    public ContestacionAutoridadResponseDTO guardar(
            String folioExpediente,
            String numeroOficio,
            String nombreTitular,
            String observaciones,
            String decision,
            byte[] archivoPDF,
            String nombreArchivo) {

        String rutaPdf = null;
        if (archivoPDF != null && archivoPDF.length > 0) {
            rutaPdf = fileUploadPort.guardarArchivoExpediente(archivoPDF, nombreArchivo);
        }

        ContestacionAutoridad contestacion = new ContestacionAutoridad();
        contestacion.setFolioExpediente(folioExpediente);
        contestacion.setNumeroOficio(numeroOficio);
        contestacion.setNombreTitular(nombreTitular);
        contestacion.setObservaciones(observaciones);
        contestacion.setDecision(decision);
        contestacion.setRutaPdfInforme(rutaPdf);

        ContestacionAutoridad saved = port.guardar(contestacion);

        quejaJpaRepository.findIdExpedienteByFolio(folioExpediente)
            .ifPresent(idExpediente ->
                quejaJpaRepository.actualizarEstatusQueja(idExpediente, EstatusQuejaIds.CONTESTACION));

        return ContestacionAutoridadResponseDTO.builder()
            .id(saved.getId())
            .folioExpediente(saved.getFolioExpediente())
            .numeroOficio(saved.getNumeroOficio())
            .nombreTitular(saved.getNombreTitular())
            .rutaPdfInforme(saved.getRutaPdfInforme())
            .observaciones(saved.getObservaciones())
            .decision(saved.getDecision())
            .build();
    }

  
}