package com.sigcqal.api.application.ModuloCorrespondencia.ReasignacionCorrespondencia;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionCorrespondencia.Port.IReasignacionCorrespondenciaPort;
import com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Dto.ReasignacionCorrespondenciaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReasignacionCorrespondenciaService {

    private final IReasignacionCorrespondenciaPort reasignacionCorrespondenciaPort;

    public Optional<ReasignacionCorrespondenciaResponseDTO> listarPorId(Long id) {
        try {
            return reasignacionCorrespondenciaPort.listarPorId(id);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public List<ReasignacionCorrespondenciaResponseDTO> listarPorArea(Long idArea) {
        try {
            return reasignacionCorrespondenciaPort.listarPorArea(idArea);
        } catch (Exception ex) {
            return List.of();
        }
    }

    public List<ReasignacionCorrespondenciaResponseDTO> listarPorEstatusRechazado(Boolean estatusRechazado) {
        try {
            return reasignacionCorrespondenciaPort.listarPorEstatusRechazado(estatusRechazado);
        } catch (Exception ex) {
            return List.of();
        }
    }
}
