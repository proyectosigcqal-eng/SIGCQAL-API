package com.sigcqal.api.application.ModuloCorrespondencia.AcuseOficio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Port.AcuseOficioRepositoryPort;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseOficio.Dto.*;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Model.AcuseOficio;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Mapper.AcuseOficioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcuseOficioService {

    private final AcuseOficioRepositoryPort repository;
    private final AcuseOficioMapper mapper;

    @Transactional
    public AcuseOficioResponseDTO crearAcuseAutomatico(AcuseOficioRequestDTO request) {
        log.info("Iniciando creación de acuse para Oficio ID: {}", request.getIdOficio());

        try {
            // 1. Validar que el ID del oficio no sea nulo
            if (request.getIdOficio() == null) {
                throw new RuntimeException("El ID del oficio es obligatorio.");
            }

            // 2. Crear instancia de dominio
            AcuseOficio acuse = new AcuseOficio();
            acuse.setIdOficio(request.getIdOficio());
            acuse.setIdUsuarioRevisor(request.getIdUsuarioRevisor());
            acuse.setEsDelArea(request.getEsDelArea());
            acuse.setFechaAceptacion(LocalDate.now());
            acuse.setHoraAceptacion(LocalTime.now());

            // 3. Guardar (El adaptador manejará la persistencia)
            AcuseOficio saved = repository.save(acuse);
            
            log.info("Acuse creado exitosamente con ID: {}", saved.getIdAcuseOficio());
            return mapper.toResponse(saved);

        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            log.error("Error de integridad: Verifique si el id_oficio {} o el id_usuario_revisor existen en la BD.", request.getIdOficio());
            throw new RuntimeException("No se pudo crear el acuse: Referencia de oficio o usuario inválida.");
        } catch (Exception e) {
            log.error("Error inesperado al crear acuse: {}", e.getMessage());
            throw new RuntimeException("Error interno al procesar el acuse: " + e.getMessage());
        }
    }

    public List<AcuseOficioResponseDTO> listarPorArea(Long idArea) {
        try {
            List<AcuseOficio> lista = repository.findByAreaAndEsDelAreaTrue(idArea);
            if (lista.isEmpty()) {
                log.warn("No se encontraron acuses marcados como 'es_del_area' para el área ID: {}", idArea);
            }
            return lista.stream()
                    .map(mapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al consultar acuses por área: {}", e.getMessage());
            throw new RuntimeException("Error al obtener la lista de acuses.");
        }
    }
}