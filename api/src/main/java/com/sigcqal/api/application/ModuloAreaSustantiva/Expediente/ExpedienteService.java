package com.sigcqal.api.application.ModuloAreaSustantiva.Expediente;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
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
    private final FileUploadPort fileUploadPort;

    @Transactional
    public ExpedienteResponseDTO guardar(ExpedienteRequestDTO request) {
        String folioGenerado = generarFolioAutomatico();

        Expediente expediente = Expediente.builder()
            .folioGobierno(folioGenerado)
                .fechaSolicitud(request.getFechaSolicitud())
                .idMunicipio(request.getIdMunicipio())
                .idAsesor(request.getIdAsesor())
                .idContribuyente(request.getIdContribuyente())
                .idSolicitante(request.getIdSolicitante())
                .idRepresentanteLegal(request.getIdRepresentanteLegal())
                .idTipoTramite(request.getIdTipoTramite())
                .idEstatusExpediente(request.getIdEstatusExpediente())
                .documentoAcreditaPersonalidad(request.getDocumentoAcreditaPersonalidad())
                .archivoDocumentoAcreditaPersonalidad(request.getArchivoDocumentoAcreditaPersonalidad())
                .build();

        Expediente guardado = port.save(expediente);
        return mapper.toResponse(guardado);
    }

    private String generarFolioAutomatico() {
        LocalDate now = LocalDate.now();
        String yy = String.valueOf(now.getYear()).substring(2);
        String mm = String.format("%02d", now.getMonthValue());
        String prefix = yy + mm;

        int nextSeq = 1;
        Optional<Expediente> last = port.findTopByFolioPrefix(prefix);
        if (last.isPresent() && last.get().getFolioGobierno() != null && last.get().getFolioGobierno().length() > prefix.length()) {
            String lastFolio = last.get().getFolioGobierno();
            String seqStr = lastFolio.substring(prefix.length());
            try {
                nextSeq = Integer.parseInt(seqStr) + 1;
            } catch (NumberFormatException e) {
                nextSeq = 1;
            }
        }

        String seqFormatted = String.format("%05d", nextSeq);
        return prefix + seqFormatted;
    }


    public List<ExpedienteResponseDTO> buscarPorFolio(String folio) {
        Optional<Expediente> resultado = port.findByFolio(folio);

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

    @Transactional
public ExpedienteResponseDTO guardarDocumentoPersonalidad(String folio, byte[] archivo) {
    Expediente expediente = port.findByFolio(folio)
        .stream().findFirst()
        .orElseThrow(() -> new RuntimeException("Expediente no encontrado: " + folio));

    String nombreArchivo = "EXP_" + folio + "_DOC_PERSONALIDAD.pdf";
    String url = fileUploadPort.guardarArchivoExpediente(archivo, nombreArchivo);
    expediente.setArchivoDocumentoAcreditaPersonalidad(url);

    Expediente guardado = port.save(expediente);
    return mapper.toResponse(guardado);
}
}