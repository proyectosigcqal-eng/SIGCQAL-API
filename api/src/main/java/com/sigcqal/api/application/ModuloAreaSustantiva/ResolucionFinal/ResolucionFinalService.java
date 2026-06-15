package com.sigcqal.api.application.ModuloAreaSustantiva.ResolucionFinal;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port.ResolucionFinalRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper.ResolucionFinalMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResolucionFinalService {
    private final ResolucionFinalRepositoryPort port;
    private final ResolucionFinalMapper mapper;

    private static final Integer ID_ESTATUS_RESOLUCION_EMITIDA = 5;

    @Transactional
    public ResolucionFinalResponseDTO emitirResolucion(ResolucionFinalRequestDTO request) {
        if (request.getRutaDocumento() == null || request.getRutaDocumento().isBlank()) {
            throw new RuntimeException("El documento firmado es obligatorio para emitir la resolución");
        }

    ResolucionFinal resolucion = ResolucionFinal.builder()
        .idExpediente(request.getIdExpediente())
        .tipoResolucion(request.getTipoResolucion())
        .fechaEmision(request.getFechaEmision())
        .rutaDocumento(request.getRutaDocumento())
        .idEstatus(ID_ESTATUS_RESOLUCION_EMITIDA)
        .build();
    
    return mapper.toResponse(port.guardarResolucion(resolucion));
}

public ResolucionFinalResponseDTO buscarPorExpediente(Integer idExpediente) {
    return port.buscarPorExpediente(idExpediente)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("No se encontró la resolución final para el expediente: " + idExpediente

        ));
}
}
