package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Adapter;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Port.NotificacionCierreyAcuerdodeRazonRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Model.NotificacionCierreyAcuerdodeRazon;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Mapper.NotificacionCierreyAcuerdodeRazonMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Repository.NotificacionCierreyAcuerdodeRazonJPARepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Repository.CierreExpedienteUpdateRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;
import com.sigcqal.api.infra.Catalogo.Usuario.Repository.UsuarioJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionCierreyAcuerdodeRazonAdapter implements NotificacionCierreyAcuerdodeRazonRepositoryPort {

    private final NotificacionCierreyAcuerdodeRazonJPARepository jpaRepository;
    private final NotificacionCierreyAcuerdodeRazonMapper         mapper;
    private final ExpedienteJPARepository                         expedienteRepository;
    private final UsuarioJpaRepository                            usuarioRepository;
    private final CierreExpedienteUpdateRepository                cierreExpedienteUpdateRepository;

    @Override
    public NotificacionCierreyAcuerdodeRazon save(NotificacionCierreyAcuerdodeRazon cierre) {

        if (cierre.getIdExpediente() == null || cierre.getIdUsuarioCierre() == null) {
            throw new IllegalArgumentException(
                "El ID del Expediente y del Usuario no pueden ser nulos");
        }

        // ★ FIX: el frontend a veces manda el folio de gobierno (ej: 260600026)
        // en lugar del id_expediente numérico real (ej: 26).
        // Intentamos primero por ID numérico; si no existe, buscamos por folio.
        ExpedienteEntity expEntity = resolverExpediente(cierre.getIdExpediente());

        // Usar el ID real del expediente (por si vino como folio)
        Integer idExpedienteReal = expEntity.getId().intValue();

        cierreExpedienteUpdateRepository.actualizarEstatusExpediente(idExpedienteReal);

        var userEntity = usuarioRepository.findById(cierre.getIdUsuarioCierre().longValue())
                .orElseThrow(() -> new RuntimeException(
                    "Usuario no encontrado con ID: " + cierre.getIdUsuarioCierre()));

        // Asegurar que el dominio tenga el ID real antes de mapear
        cierre.setIdExpediente(idExpedienteReal);

        var entity        = mapper.toEntity(cierre, expEntity, userEntity);
        var entityGuardada = jpaRepository.save(entity);

        return mapper.toDomain(entityGuardada);
    }

    @Override
    public List<NotificacionCierreyAcuerdodeRazon> findById(Integer idExpediente) {
        return jpaRepository.findByExpediente_IdConRelaciones(idExpediente).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Resuelve el ExpedienteEntity a partir de un valor que puede ser:
     *   a) el id_expediente numérico real  (ej: 26)
     *   b) el folio_gobierno como número   (ej: 260600026)
     *
     * Estrategia:
     *   1. Buscar por ID numérico — si existe, usarlo.
     *   2. Si no existe, buscar por folio_gobierno (convirtiendo el Integer a String).
     *   3. Si tampoco existe, lanzar excepción con mensaje claro.
     */
    private ExpedienteEntity resolverExpediente(Integer valor) {
        // Intento 1: búsqueda directa por PK
        Optional<ExpedienteEntity> porId = expedienteRepository.findById(valor);
        if (porId.isPresent()) {
            return porId.get();
        }

        // Intento 2: el valor es en realidad el folio_gobierno
        String folioComoString = String.valueOf(valor);
        log.warn("[Cierre] id_expediente={} no encontrado por PK, intentando como folio_gobierno={}",
                valor, folioComoString);

        return expedienteRepository.findByFolioGobierno(folioComoString)
                .orElseThrow(() -> new RuntimeException(
                    "Expediente no encontrado. Se buscó por id=" + valor
                    + " y por folio_gobierno='" + folioComoString + "'. "
                    + "Verifica que el frontend mande el id_expediente numérico real."));
    }
}