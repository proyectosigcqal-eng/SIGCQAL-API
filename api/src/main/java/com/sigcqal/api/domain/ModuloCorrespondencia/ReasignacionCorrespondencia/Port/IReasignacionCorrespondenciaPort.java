package com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionCorrespondencia.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Dto.ReasignacionCorrespondenciaResponseDTO;

public interface IReasignacionCorrespondenciaPort {

    Optional<ReasignacionCorrespondenciaResponseDTO> listarPorId(Long id);

    List<ReasignacionCorrespondenciaResponseDTO> listarPorArea(Long idArea);

    List<ReasignacionCorrespondenciaResponseDTO> listarPorEstatusRechazado(Boolean estatusRechazado);
}
