package com.sigcqal.api.application.ModuloAreaSustantiva.Queja;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Port.QuejaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Mapper.QuejaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.Queja.DTO.QuejaResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuejaService {

    private final QuejaRepositoryPort port;
    private final QuejaMapper mapper;
    private final QuejaJPARepository quejaRepo; 

    public QuejaResponseDTO obtenerPorId(Integer idQueja) {
        return port.findById(idQueja)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Queja no encontrada con el ID: " + idQueja));
    }

    public List<QuejaResponseDTO> listarTodas() {
        return port.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
@Transactional
public void admitirQueja(String folio) {
    Integer idExpediente = quejaRepo.findIdExpedienteByFolio(folio)
            .orElseThrow(() -> new RuntimeException(
                    "No se encontró expediente con folio: " + folio));

    Integer idDetalleAsesoria = quejaRepo.findIdDetalleByExpediente(idExpediente)
            .orElseThrow(() -> new RuntimeException(
                    "No se encontró detalle de asesoría para: " + folio));

    port.admitir(idDetalleAsesoria, idExpediente);
    quejaRepo.marcarProcede(idExpediente); // ← nuevo: estatus = 1 "Asignada a Asesor"
}

@Transactional
public void requerirAclaracion(String folio) {
    Integer idExpediente = quejaRepo.findIdExpedienteByFolio(folio)
            .orElseThrow(() -> new RuntimeException(
                    "No se encontró expediente con folio: " + folio));

    port.requerirAclaracion(idExpediente);
}

@Transactional
public void actualizarRequisitos(String folio, Boolean identificacion,
                                  Boolean actosFiscales, Boolean narrativa,
                                  Boolean competencia) {
    // ← upsert: crea si no existe, actualiza si existe
    quejaRepo.upsertRequisitos(folio, identificacion,
                               actosFiscales, narrativa, competencia);
}
}