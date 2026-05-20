package com.sigcqal.api.application.ModuloCorrespondencia.OficioContestacionExterna;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Model.OficioContestacionExterna;
import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Port.OficioContestacionExternaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Mapper.OficioContestacionExternaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.OficioContestacionExterna.Dto.OficioContestacionExternaDTOs;

@Service
public class OficioContestacionExternaService {
    @Autowired
    private OficioContestacionExternaRepositoryPort repositoryPort;

    @Autowired
    private OficioContestacionExternaMapper mapper;

    @Autowired
    private FileUploadPort fileUploadPort;

    public OficioContestacionExternaDTOs.Response guardar(OficioContestacionExternaDTOs.Request request) {
        validarRequest(request);

        OficioContestacionExterna dom = new OficioContestacionExterna();
        dom.setIdCorrespondencia(request.getIdCorrespondencia());
        dom.setIdUsuarioEmisor(request.getIdUsuarioEmisor());
        dom.setNumOficioSalida(request.getNumOficioSalida() != null ? request.getNumOficioSalida().trim() : null);
        dom.setAsuntoContestacion(request.getAsuntoContestacion());
        dom.setCuerpoOficioTexto(request.getCuerpoOficioTexto());
        dom.setUrlPdfFinal(request.getUrlPdfFinal());

        repositoryPort.buscarPorCorrespondencia(request.getIdCorrespondencia())
                .ifPresent(existente -> dom.setIdOficioContestacion(existente.getIdOficioContestacion()));

        OficioContestacionExterna saved = repositoryPort.guardar(dom);
        return mapper.toResponse(saved);
    }

    public OficioContestacionExternaDTOs.Response guardarPdfFinal(Long idCorrespondencia, byte[] pdfBytes) {
        if (idCorrespondencia == null || idCorrespondencia <= 0) {
            throw new InvalidRequestException("El idCorrespondencia debe ser mayor a 0");
        }
        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new InvalidRequestException("El archivo PDF es obligatorio");
        }

        OficioContestacionExterna existente = repositoryPort.buscarPorCorrespondencia(idCorrespondencia)
                .orElseThrow(() -> new InvalidRequestException("No existe OficioContestacionExterna para la correspondencia: " + idCorrespondencia));

        String nombreArchivo = "OFICIO_CONTESTACION_" + idCorrespondencia + "_FIRMADO.pdf";
        String url = fileUploadPort.guardarArchivoOficio(pdfBytes, nombreArchivo);
        existente.setUrlPdfFinal(url);

        OficioContestacionExterna saved = repositoryPort.guardar(existente);
        return mapper.toResponse(saved);
    }

    public Optional<OficioContestacionExternaDTOs.Response> buscarPorCorrespondencia(Long idCorrespondencia) {
        if (idCorrespondencia == null || idCorrespondencia <= 0) {
            throw new InvalidRequestException("El idCorrespondencia debe ser mayor a 0");
        }

        return repositoryPort.buscarPorCorrespondencia(idCorrespondencia).map(mapper::toResponse);
    }

    public List<OficioContestacionExternaDTOs.Response> listarTodos() {
        return repositoryPort.listarTodos().stream().map(mapper::toResponse).toList();
    }

    private void validarRequest(OficioContestacionExternaDTOs.Request request) {
        if (request == null) {
            throw new InvalidRequestException("El request no puede ser null");
        }
        if (request.getIdCorrespondencia() == null || request.getIdCorrespondencia() <= 0) {
            throw new InvalidRequestException("El idCorrespondencia debe ser mayor a 0");
        }
        if (request.getIdUsuarioEmisor() == null || request.getIdUsuarioEmisor() <= 0) {
            throw new InvalidRequestException("El idUsuarioEmisor debe ser mayor a 0");
        }
        if (request.getNumOficioSalida() == null || request.getNumOficioSalida().trim().isEmpty()) {
            throw new InvalidRequestException("El numOficioSalida es obligatorio");
        }
    }
}
