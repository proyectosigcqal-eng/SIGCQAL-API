package com.sigcqal.api.application.ModuloAreaSustantiva.IrlDemandaAmparo;


import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port.ExpedienteRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Model.IrlDemandaAmparo;
import com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Port.IrlDemandaAmparoRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Repository.IrlDemandaAmparoJpaRepository;
import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto.IrlDemandaAmparoRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto.IrlDemandaAmparoResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto.SemaforoJudicialDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class IrlDemandaAmparoService {

    private final IrlDemandaAmparoRepositoryPort port;
    private final GeneradorDocumentoService       generadorDocumentoService;
    private final FileUploadPort                  fileUploadPort;
    private final SemaforoJudicialService         semaforoJudicialService;
    private final ExpedienteRepositoryPort expedientePort;
    private final IrlDemandaAmparoJpaRepository repository;

    private static final String PLANTILLA = "plantilla_demanda_amparo.docx";

    // ── GUARDAR ────────────────────────────────────────────────────────
  public IrlDemandaAmparoResponseDTO guardar(IrlDemandaAmparoRequestDTO req) {

    validarMultasHistoricas(req);

    // Resolver idExpediente desde folio si no viene directo
    Integer idExpediente = req.getIdExpediente();
    if (idExpediente == null && req.getFolioExpediente() != null) {
        idExpediente = expedientePort.findByFolio(req.getFolioExpediente())
                .map(Expediente::getId)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró expediente con folio: " + req.getFolioExpediente()));
    }
    if (idExpediente == null) {
        throw new InvalidRequestException("Se requiere idExpediente o folioExpediente.");
    }

    // idRepresentacionLegal queda null hasta que el otro equipo entregue su flujo
    // NO usar getIdRepresentanteLegal() del expediente — eso es otra cosa

    IrlDemandaAmparo domain = IrlDemandaAmparo.builder()
            .idExpediente(idExpediente)
            .idRepresentacionLegal(null)  // ← null forzado, correcto
            .autoridadReclamadaMunicipio(req.getAutoridadReclamadaMunicipio())
            .superficieTerreno(req.getSuperficieTerreno())
            .superficieConstruccion(req.getSuperficieConstruccion())
            .tipoConstruccion(req.getTipoConstruccion())
            .zonificacion(req.getZonificacion())
            .folioReciboPago(req.getFolioReciboPago())
            .montoPago(req.getMontoPago())
            .fechaPrimerPago(req.getFechaPrimerPago())
            .incluyeMultasHistoricas(req.getIncluyeMultasHistoricas())
            .aniosMultasHistoricas(
                Boolean.TRUE.equals(req.getIncluyeMultasHistoricas())
                    ? req.getAniosMultasHistoricas() : null)
            .argumentacionFaltaNotificacion(
                Boolean.TRUE.equals(req.getIncluyeMultasHistoricas())
                    ? req.getArgumentacionFaltaNotificacion() : null)
            .transcripcionLeyIngresos(req.getTranscripcionLeyIngresos())
            .fechaRegistro(LocalDateTime.now())
            .ultimaActualizacion(LocalDateTime.now())
          
            .build();

    return toResponseDTO(port.save(domain));
}
    // ── GENERAR DOCX ───────────────────────────────────────────────────
    public IrlDemandaAmparoResponseDTO generarDemanda(Integer idDemandaAmparo) {
         IrlDemandaAmparo demanda = port.findByIdEnriquecido(idDemandaAmparo)
            .orElseThrow(() -> new RuntimeException(
                    "Demanda de amparo no encontrada: " + idDemandaAmparo));

                     

          try {
        Map<String, String> variables = construirVariables(demanda);
        byte[] bytes = generadorDocumentoService
                .generarDesPlantilla(PLANTILLA, variables);

        String nombreArchivo = "AMPARO_" + idDemandaAmparo
                + "_" + System.currentTimeMillis() + ".docx";
        String ruta = fileUploadPort.guardarArchivoAmparo(bytes, nombreArchivo);

        demanda.setRutaPdfDemandaGenerada(ruta);
        demanda.setFechaGeneracionDemanda(LocalDateTime.now());
        demanda.setUltimaActualizacion(LocalDateTime.now());

        return toResponseDTO(port.save(demanda));

    } catch (Exception e) {
        log.error("[IrlDemandaAmparo] Error generando docx para id {}: {}",
                idDemandaAmparo, e.getMessage());
        throw new RuntimeException(
                "Error al generar la demanda de amparo: " + e.getMessage(), e);
    }
}
    // ── CARGAR DEMANDA PRESENTADA (SCRUM-2.3.4) ───────────────────────
    public IrlDemandaAmparoResponseDTO cargarDemandaPresentada(
            Integer idDemandaAmparo,
            MultipartFile demandaPdf,
            MultipartFile acusePdf) {

        IrlDemandaAmparo demanda = port.findById(idDemandaAmparo)
                .orElseThrow(() -> new RuntimeException(
                        "Demanda de amparo no encontrada: " + idDemandaAmparo));
        try {
            if (demandaPdf != null && !demandaPdf.isEmpty()) {
                String rutaDemanda = fileUploadPort.guardarArchivoExpediente(
                        demandaPdf.getBytes(),
                        "DEMANDA_PRESENTADA_" + idDemandaAmparo + ".pdf");
                demanda.setRutaPdfDemandaPresentada(rutaDemanda);
            }
            if (acusePdf != null && !acusePdf.isEmpty()) {
                String rutaAcuse = fileUploadPort.guardarArchivoExpediente(
                        acusePdf.getBytes(),
                        "ACUSE_DEMANDA_" + idDemandaAmparo + ".pdf");
                demanda.setRutaPdfAcuseDemanda(rutaAcuse);
            }
            demanda.setFechaPresentacionDemanda(LocalDate.now());
            demanda.setUltimaActualizacion(LocalDateTime.now());

            return toResponseDTO(port.save(demanda));

        } catch (Exception e) {
            log.error("[IrlDemandaAmparo] Error cargando demanda presentada id {}: {}",
                    idDemandaAmparo, e.getMessage());
            throw new RuntimeException(
                    "Error al cargar la demanda presentada: " + e.getMessage(), e);
        }
    }

    // ── SEMÁFORO (SCRUM-2.3.3) ─────────────────────────────────────────
    public SemaforoJudicialDTO obtenerSemaforo(Integer idDemandaAmparo) {
        IrlDemandaAmparo demanda = port.findById(idDemandaAmparo)
                .orElseThrow(() -> new RuntimeException(
                        "Demanda de amparo no encontrada: " + idDemandaAmparo));

        if (demanda.getFechaPrimerPago() == null) {
            throw new InvalidRequestException(
                    "No hay fecha de primer pago registrada para calcular el semáforo.");
        }
        return semaforoJudicialService.calcular(demanda.getFechaPrimerPago());
    }

    // ── BUSCAR ─────────────────────────────────────────────────────────
    public IrlDemandaAmparoResponseDTO buscarPorId(Integer id) {
        return port.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException(
                        "Demanda de amparo no encontrada: " + id));
    }

    public IrlDemandaAmparoResponseDTO buscarPorExpediente(Integer idExpediente) {
        return port.findByIdExpediente(idExpediente)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException(
                        "No existe demanda de amparo para expediente: " + idExpediente));
    }

    // ──────────────────────────────────────────────────────────────────
    // PRIVADOS
    // ──────────────────────────────────────────────────────────────────

    // 2.3.2 — Valida switch de multas históricas
    private void validarMultasHistoricas(IrlDemandaAmparoRequestDTO req) {
        if (!Boolean.TRUE.equals(req.getIncluyeMultasHistoricas())) return;

        if (req.getAniosMultasHistoricas() == null
                || req.getAniosMultasHistoricas().isBlank()) {
            throw new InvalidRequestException(
                    "Debe indicar los años de multas históricas (2016-2023).");
        }
        if (req.getArgumentacionFaltaNotificacion() == null
                || req.getArgumentacionFaltaNotificacion().isBlank()) {
            throw new InvalidRequestException(
                    "Debe incluir la argumentación por falta de notificación.");
        }
        // Valida que los años estén dentro del rango permitido 2016-2023
        String[] anios = req.getAniosMultasHistoricas().split(",");
        for (String anio : anios) {
            int a = Integer.parseInt(anio.trim());
            if (a < 2016 || a > 2023) {
                throw new InvalidRequestException(
                        "El año " + a + " está fuera del rango permitido (2016-2023).");
            }
        }
    }

    // 2.3.2 — Construye variables para el docx, inyectando multas solo si aplica
   private Map<String, String> construirVariables(IrlDemandaAmparo d) {
    Map<String, String> v = new LinkedHashMap<>();
    // Datos del quejoso — vienen del expediente (precargados)
    v.put("{{NOMBRE_QUEJOSO}}",       nvl(d.getNombreQuejoso()));
    v.put("{{CALLE_QUEJOSO}}",        nvl(d.getCalleQuejoso()));
    v.put("{{COLONIA_QUEJOSO}}",      nvl(d.getColoniaQuejoso()));
    v.put("{{NUM_CALLE_QUEJOSO}}",   nvl(d.getNumCalleQuejoso()));
v.put("{{CP_QUEJOSO}}",          nvl(d.getCpQuejoso()));
    // Autoridad reclamada
    v.put("{{MUNICIPIO_AUTORIDAD}}", nvl(d.getAutoridadReclamadaMunicipio()));
    // Datos del predio / catastro
    v.put("{{SUPERFICIE_TERRENO}}",      nvl(d.getSuperficieTerreno()));
    v.put("{{SUPERFICIE_CONSTRUCCION}}", nvl(d.getSuperficieConstruccion()));
    v.put("{{ZONIFICACION}}",            nvl(d.getZonificacion()));
    v.put("{{TIPO_CONSTRUCCION}}",       nvl(d.getTipoConstruccion()));
    // Actos de aplicación
    v.put("{{FOLIO_RECIBO_1}}",     nvl(d.getFolioReciboPago()));
    v.put("{{FOLIO_RECIBO_2}}",     "");   // segundo folio si aplica, de momento vacío
    v.put("{{NUM_RECIBO_1}}",       "");
    v.put("{{NUM_RECIBO_2}}",       "");
    v.put("{{CLAVE_PREDIAL}}",      nvl(d.getClavePredial()));
    v.put("{{NUM_CUENTA}}",         nvl(d.getNumCuenta()));
    v.put("{{FECHA_PRIMER_PAGO}}",  nvl(d.getFechaPrimerPago()));
    v.put("{{FOLIO_RECIBO_1}}",      nvl(d.getFolioReciboPago()));
v.put("{{FOLIO_RECIBO_2}}",      nvl(d.getFolioReciboPago2()));
v.put("{{NUM_RECIBO_1}}",        nvl(d.getNumRecibo1()));
v.put("{{NUM_RECIBO_2}}",        nvl(d.getNumRecibo2()));
v.put("{{CLAVE_PREDIAL}}",       nvl(d.getClavePredial()));
v.put("{{NUM_CUENTA}}",          nvl(d.getNumCuenta()));
v.put("{{DOMICILIO_AUTORIDAD}}", nvl(d.getDomicilioAutoridad()));
    // Ejercicios fiscales — se calculan a partir de la fecha de pago
    int anoPago = d.getFechaPrimerPago() != null ? d.getFechaPrimerPago().getYear() : 0;
    v.put("{{EJ_FISCAL_ANTERIOR}}", anoPago > 0 ? String.valueOf(anoPago - 1) : "");
    v.put("{{EJ_FISCAL_ACTUAL}}",   anoPago > 0 ? String.valueOf(anoPago)     : "");
    // Decretos (fijos — se actualizan anualmente)
    v.put("{{FECHA_PUBLICACION_DECRETO_ANTERIOR}}", "30 de diciembre de 2023");
    v.put("{{FECHA_PUBLICACION_DECRETO_ACTUAL}}",   "28 de diciembre de 2024");
    // Transcripción de ley (rich text del asesor)
    v.put("{{TRANSCRIPCION_LEY}}", nvl(d.getTranscripcionLeyIngresos()));
    v.put("{{FECHA_ACTUAL}}",      generadorDocumentoService.fechaActual());
    // Multas históricas (condicional)
    if (Boolean.TRUE.equals(d.getIncluyeMultasHistoricas())) {
        v.put("{{ANIOS_MULTAS_HISTORICAS}}", nvl(d.getAniosMultasHistoricas()));
        v.put("{{ARGUMENTACION_FALTA_NOTIFICACION}}", nvl(d.getArgumentacionFaltaNotificacion()));
    } else {
        v.put("{{ANIOS_MULTAS_HISTORICAS}}", "");
        v.put("{{ARGUMENTACION_FALTA_NOTIFICACION}}", "");
    }
    return v;
}
    private String nvl(Object v) {
        return v != null ? v.toString() : "";
    }

    private IrlDemandaAmparoResponseDTO toResponseDTO(IrlDemandaAmparo d) {
        return IrlDemandaAmparoResponseDTO.builder()
                .idDemandaAmparo(d.getIdDemandaAmparo())
                .idExpediente(d.getIdExpediente())
                .idRepresentacionLegal(d.getIdRepresentacionLegal())
                .autoridadReclamadaMunicipio(d.getAutoridadReclamadaMunicipio())
                .superficieTerreno(d.getSuperficieTerreno())
                .superficieConstruccion(d.getSuperficieConstruccion())
                .tipoConstruccion(d.getTipoConstruccion())
                .zonificacion(d.getZonificacion())
                .folioReciboPago(d.getFolioReciboPago())
                .montoPago(d.getMontoPago())
                .fechaPrimerPago(d.getFechaPrimerPago())
                .incluyeMultasHistoricas(d.getIncluyeMultasHistoricas())
                .aniosMultasHistoricas(d.getAniosMultasHistoricas())
                .rutaPdfDemandaGenerada(d.getRutaPdfDemandaGenerada())
                .rutaPdfDemandaPresentada(d.getRutaPdfDemandaPresentada())
                .rutaPdfAcuseDemanda(d.getRutaPdfAcuseDemanda())
                .fechaPresentacionDemanda(d.getFechaPresentacionDemanda())
                .fechaRegistro(d.getFechaRegistro())
                .build();
    }
}