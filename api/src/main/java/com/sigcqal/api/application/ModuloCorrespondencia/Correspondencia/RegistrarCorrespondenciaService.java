package com.sigcqal.api.application.ModuloCorrespondencia.Correspondencia;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Model.Correspondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port.AuditoriaPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port.CorrespondenciaRepositoryPort;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.RegistrarCorrespondenciaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.RegistrarCorrespondenciaResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class RegistrarCorrespondenciaService {
    private static final Long ESTATUS_REGISTRADO_ID = 1L;

    @Autowired
    private CorrespondenciaRepositoryPort repositoryPort;

    @Autowired
    private AuditoriaPort auditoriaPort;

    @Transactional
    public RegistrarCorrespondenciaResponseDTO registrar(RegistrarCorrespondenciaRequestDTO request) {
        validarRequest(request);

        if (repositoryPort.existsByNumeroOficio(request.getNumeroOficio())) {
            throw new InvalidRequestException("El número de oficio ya existe en el sistema");
        }

        Correspondencia saved = guardarConFolioUnico(request);
        auditoriaPort.registrarRegistroCorrespondencia(saved.getId(), saved.getIdUsuarioCaptura(), saved.getFolioUnico());

        RegistrarCorrespondenciaResponseDTO response = new RegistrarCorrespondenciaResponseDTO();
        response.setConsecutivo(saved.getConsecutivo());
        response.setFolioUnico(saved.getFolioUnico());
        response.setMensaje("Correspondencia registrada exitosamente. Consecutivo: " + saved.getConsecutivo());
        response.setIdCorrespondencia(saved.getId());
        return response;
    }

    public RegistrarCorrespondenciaResponseDTO obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Correspondencia dom = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Correspondencia", id));
        RegistrarCorrespondenciaResponseDTO response = new RegistrarCorrespondenciaResponseDTO();
        response.setIdCorrespondencia(dom.getId());
        response.setConsecutivo(dom.getConsecutivo());
        response.setFolioUnico(dom.getFolioUnico());
        response.setMensaje("OK");
        return response;
    }

    private Correspondencia guardarConFolioUnico(RegistrarCorrespondenciaRequestDTO request) {
        Integer anio = request.getFechaRecibido().getYear();
        int intentos = 0;
        while (intentos < 3) {
            intentos++;

            Long last = repositoryPort.findLastConsecutivoByAnio(anio).orElse(0L);
            Long consecutivo = last + 1;
            String folio = "CEDECON-CORR-" + consecutivo + "/" + anio;

            Correspondencia dom = new Correspondencia();
            dom.setConsecutivo(consecutivo);
            dom.setFolioUnico(folio);
            dom.setNumeroOficio(request.getNumeroOficio());
            dom.setFechaExpedicion(request.getFechaExpedicion());
            dom.setDependenciaRemitente(request.getDependenciaRemitente());
            dom.setTitularDependencia(request.getTitularDependencia());
            dom.setAsunto(request.getAsunto());
            dom.setFechaRecibido(request.getFechaRecibido());
            dom.setIdEstatus(ESTATUS_REGISTRADO_ID);
            dom.setIdUsuarioCaptura(request.getIdUsuarioCaptura());

            try {
                return repositoryPort.save(dom);
            } catch (DataIntegrityViolationException ex) {
                if (intentos >= 3) {
                    throw new InvalidRequestException("No fue posible generar un folio único. Reintenta");
                }
            }
        }

        throw new InvalidRequestException("No fue posible generar un folio único. Reintenta");
    }

    private void validarRequest(RegistrarCorrespondenciaRequestDTO request) {
        if (request == null) {
            throw new InvalidRequestException("La solicitud no puede ser nula");
        }

        if (isBlank(request.getNumeroOficio())) {
            throw new InvalidRequestException("El número de oficio es obligatorio");
        }

        if (request.getFechaExpedicion() == null) {
            throw new InvalidRequestException("La fecha de expedición es obligatoria");
        }

        if (isBlank(request.getDependenciaRemitente())) {
            throw new InvalidRequestException("La dependencia remitente es obligatoria");
        }

        if (isBlank(request.getTitularDependencia())) {
            throw new InvalidRequestException("El titular de la dependencia es obligatorio");
        }

        if (isBlank(request.getAsunto())) {
            throw new InvalidRequestException("El asunto es obligatorio");
        }

        if (request.getFechaRecibido() == null) {
            throw new InvalidRequestException("La fecha de recibido es obligatoria");
        }

        if (request.getIdUsuarioCaptura() == null || request.getIdUsuarioCaptura() <= 0) {
            throw new InvalidRequestException("El usuario de captura es obligatorio");
        }

        LocalDate expedicion = request.getFechaExpedicion();
        LocalDate recibido = request.getFechaRecibido();
        if (expedicion != null && recibido != null && expedicion.isAfter(recibido)) {
            throw new InvalidRequestException("La fecha de expedición no puede ser posterior a la fecha de recibido");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
