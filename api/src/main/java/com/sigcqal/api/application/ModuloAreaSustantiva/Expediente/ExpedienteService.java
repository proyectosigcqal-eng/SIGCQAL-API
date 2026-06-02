package com.sigcqal.api.application.ModuloAreaSustantiva.Expediente;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port.ExpedienteRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Mapper.ExpedienteMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO.ExpedienteRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO.ExpedienteResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpedienteService {

    private final ExpedienteRepositoryPort port;
    private final ExpedienteMapper mapper;

    @Transactional
    public ExpedienteResponseDTO guardar(ExpedienteRequestDTO request) {

        Expediente expediente = Expediente.builder()
                .folioGobierno(request.getFolioGobierno())
                .fechaSolicitud(request.getFechaSolicitud())
                .idMunicipio(request.getIdMunicipio())
                .idAsesor(request.getIdAsesor())
                .idContribuyente(request.getIdContribuyente())
                .idSolicitante(request.getIdSolicitante())
                .idTipoTramite(request.getIdTipoTramite())
                .idEstatusExpediente(request.getIdEstatusExpediente())
                .documentoAcreditaPersonalidad(request.getDocumentoAcreditaPersonalidad())
                .archivoDocumentoAcreditaPersonalidad(request.getArchivoDocumentoAcreditaPersonalidad())
                .build();

        Expediente guardado = port.save(expediente);
        return mapper.toResponse(guardado);
    }


    public List<ExpedienteResponseDTO> buscarPorFolio(String folio) {
        List<Expediente> resultado = port.findByFolio(folio);

        if(resultado.isEmpty()){
            throw new RuntimeException("Folio no encontrado ");
        }
       return resultado.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public java.util.List<ExpedienteResponseDTO> listarTodos() {
        return port.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }
}