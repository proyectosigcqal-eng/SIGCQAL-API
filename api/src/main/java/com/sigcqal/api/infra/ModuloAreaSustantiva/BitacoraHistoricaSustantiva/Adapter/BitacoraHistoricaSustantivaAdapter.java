package com.sigcqal.api.infra.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Adapter;

import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Entity.QuejasAriEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Repository.QuejasAriJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Repository.QuejasAcciJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.OficioAutoridad.Repository.OficioAutoridadJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Mapper.BitacoraHistoricaSustantivaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Repository.ContestacionAutoridadJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Repository.ResolucionFinalJPARepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Repository.NotificacionCierreyAcuerdodeRazonJPARepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Entity.ConstanciaInternaRemisionEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Repository.ConstanciaInternaRemisionJpaRepository;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Model.BitacoraHistoricaSustantiva;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Port.BitacoraHistoricaSustantivaRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Persona.Repository.PersonaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component("bitacoraSustantivaAdapter")
@RequiredArgsConstructor
public class BitacoraHistoricaSustantivaAdapter implements BitacoraHistoricaSustantivaRepositoryPort {

    private final QuejaJPARepository quejaRepo;
    private final QuejasAriJpaRepository quejasAriRepo;
    private final OficioAutoridadJpaRepository oficioAutoridadRepo;
    private final QuejasAcciJpaRepository quejasAcciRepo;
    private final ContestacionAutoridadJpaRepository contestacionAutoridadRepo;
    private final ResolucionFinalJPARepository resolucionFinalRepo;
    private final NotificacionCierreyAcuerdodeRazonJPARepository notificacionCierreyAcuerdodeRazonRepo;
    private final PersonaJpaRepository personaRepo;
    private final BitacoraHistoricaSustantivaMapper mapper;
    private final ConstanciaInternaRemisionJpaRepository constanciaInternaRemisionRepo;


    @Override
    public List<BitacoraHistoricaSustantiva> obtenerHistorialIntegral(Integer idQueja) {
        List<BitacoraHistoricaSustantiva> historial = new ArrayList<>();

        // 1. Registro de Queja y todo lo que dependa de ella
        quejaRepo.findById(idQueja).ifPresent(q -> {
            historial.add(mapper.mapQuejaToDomain(q));

            if (q.getExpediente() != null && q.getExpediente().getId() != null) {
                Integer idExp = q.getExpediente().getId();

                // 6. Resolución Final
                resolucionFinalRepo.findAll().stream()
                    .filter(res -> res.getIdExpediente() != null && res.getIdExpediente().equals(idExp))
                    .forEach(res -> historial.add(mapper.mapResolucionToDomain(res)));

                // 8. Notificación Cierre
                notificacionCierreyAcuerdodeRazonRepo.findAll().stream()
                    .filter(not -> not.getExpediente() != null && not.getExpediente().getId().equals(idExp))
                    .forEach(not -> {
                        String nombre = "Sistema";
                        if (not.getUsuarioCierre() != null && not.getUsuarioCierre().getIdPersona() != null) {
                            nombre = personaRepo.findById(not.getUsuarioCierre().getIdPersona())
                                    .map(p -> p.getNombre() + " " + p.getApellidoPaterno())
                                    .orElse("Sistema");
                        }
                        historial.add(mapper.mapNotificacionToDomain(not, nombre));
                    });

            
            
                constanciaInternaRemisionRepo.findAll().stream()
                    .filter(cir -> cir.getExpediente() != null && cir.getExpediente().getId() != null 
                                && cir.getExpediente().getId().equals(idExp))
                    .forEach(cir -> {
                        historial.add(mapper.mapCirToDomain(cir));
                    });
        }
        });

        // 2. ARI
        List<QuejasAriEntity> listaAri = quejasAriRepo.findByIdQueja(Long.valueOf(idQueja));
        if (listaAri != null && !listaAri.isEmpty()) {
            listaAri.forEach(ari -> historial.add(mapper.mapAriToDomain(ari)));
        }

        // 4. ACCI
        quejasAcciRepo.findByQueja_IdQueja(idQueja).forEach(acci -> 
            historial.add(mapper.mapAcciToDomain(acci)));

        // 5. Respuestas Autoridad
        contestacionAutoridadRepo.findAll().stream()
            .filter(resp -> resp.getIdQueja() != null && resp.getIdQueja().equals(idQueja))
            .forEach(resp -> historial.add(mapper.mapRespuestaToDomain(resp)));

        // 7. Oficios Autoridad
        oficioAutoridadRepo.findAll().stream()
            .filter(oficio -> oficio.getAri() != null && oficio.getAri().getIdQueja() != null 
                        && oficio.getAri().getIdQueja().equals(Long.valueOf(idQueja)))
            .forEach(oficio -> {
                // Validamos antes de procesar
                if (oficio.getFechaEnvioOficio() != null) {
                    historial.add(mapper.mapOficioToDomain(oficio));
                } else {
                    validarIntegridadFecha(oficio, null, "OficioAutoridad");
                }

        });
        
        // 6. Ordenamiento final
        return historial.stream()
                .sorted(Comparator.comparing(BitacoraHistoricaSustantiva::getFecha))
                .collect(Collectors.toList());
    }

    private static final Logger log = LoggerFactory.getLogger(BitacoraHistoricaSustantivaAdapter.class);

    private void validarIntegridadFecha(Object entidad, java.time.LocalDateTime fecha, String tipo) {
        if (fecha == null) {
            // Aquí puedes usar un Logger si tienes uno configurado
            log.warn("Advertencia: Entidad " + tipo + " tiene fecha nula. Se omitirá del historial.");
        }
    }
}
