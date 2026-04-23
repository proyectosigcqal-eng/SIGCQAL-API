package com.sigcqal.api.application.ModuloCorrespondencia.Correspondencia;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.ModuloCorrespondencia.BitacoraHistorica.Port.BitacoraHistoricaRepositoryPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Model.Correspondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port.AuditoriaPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port.CorrespondenciaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Mapper.CorrespondenciaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.RegistrarCorrespondenciaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.RegistrarCorrespondenciaResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class RegistrarCorrespondenciaService {
    private static final Long ESTATUS_REGISTRADO_ID = 1L;

    @Autowired
    private CorrespondenciaRepositoryPort repositoryPort;

    @Autowired
    private BitacoraHistoricaRepositoryPort auditoriaPort;

    @Autowired
    private CorrespondenciaMapper mapper;

    @Transactional
    public RegistrarCorrespondenciaResponseDTO registrar(RegistrarCorrespondenciaRequestDTO request) {
        validarRequest(request);

        if (repositoryPort.existsByNumeroOficio(request.getNumeroOficio())) {
            throw new InvalidRequestException("El número de oficio ya existe en el sistema");
        }

        Correspondencia correspondencia = new Correspondencia();        
        correspondencia.setConsecutivo(request.getConsecutivo());
        correspondencia.setFolioUnico(request.getFolioUnico());
        correspondencia.setId(request.getId());
        correspondencia.setIdUsuarioCaptura(request.getIdUsuarioCaptura());
        correspondencia.setIdArea(request.getIdArea());
        correspondencia.setNombreArea(request.getNombreArea());
        correspondencia.setObservaciones(request.getObservaciones());
        correspondencia.setIdEstatus(request.getIdEstatus());
        correspondencia.setNumeroOficio(request.getNumeroOficio());
        correspondencia.setFechaExpedicion(request.getFechaExpedicion());
        correspondencia.setDependenciaRemitente(request.getDependenciaRemitente());
        correspondencia.setTitularDependencia(request.getTitularDependencia());
        correspondencia.setAsunto(request.getAsunto());
        correspondencia.setFechaRecibido(request.getFechaRecibido());
        Correspondencia saved = repositoryPort.save(correspondencia);
        return mapper.toResponse(saved);
    }

    public RegistrarCorrespondenciaResponseDTO obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Correspondencia dom = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Correspondencia", id));

        return repositoryPort.findById(id).map(mapper::toResponse).orElseThrow(() -> new ResourceNotFoundException("Correspondencia", id));
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
