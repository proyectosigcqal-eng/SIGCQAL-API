package com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sigcqal.api.application.ModuloAreaSustantiva.BandejaAsesoria.BandejaAsesoriaService;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.BandejaAsesoriaResponseDto;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.UltimaModificacionDto;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Model.TramiteBandeja;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tramites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BandejaAsesoriaController {

    private final BandejaAsesoriaService service;

    @GetMapping("/bandeja")
    public ResponseEntity<List<BandejaAsesoriaResponseDto>> obtenerBandeja(
        @RequestParam(required = false, defaultValue = "") String search,
        @RequestParam(required = false, defaultValue = "") String estatus,
        @RequestParam(name = "tipo_tramite", required = false, defaultValue = "") String tipoTramite
    ) {
        List<TramiteBandeja> bandeja = service.obtenerBandeja(
            search.isEmpty() ? null : search,
            estatus.isEmpty() ? null : estatus,
            tipoTramite.isEmpty() ? null : tipoTramite
        );

        List<BandejaAsesoriaResponseDto> response = bandeja.stream()
            .map(this::toDto)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private BandejaAsesoriaResponseDto toDto(TramiteBandeja tramite) {
        UltimaModificacionDto ultimaModificacion = UltimaModificacionDto.builder()
            .descripcion(tramite.getUltimaModificacionDescripcion())
            .timestamp(tramite.getUltimaModificacionTimestamp())
            .build();

        return BandejaAsesoriaResponseDto.builder()
            .folio(tramite.getFolio())
            .municipioProcedencia(tramite.getMunicipioProcedencia())
            .contribuyente(tramite.getContribuyente())
            .tipoActo(tramite.getTipoActo())
            .estatusPrincipal(tramite.getEstatusPrincipal())
            .estatusSecundario(tramite.getEstatusSecundario())
            .ultimaModificacion(ultimaModificacion)
            .tieneBitacora(tramite.getTieneBitacora())
            .tieneFicha(tramite.getTieneFicha())
            .build();
    }
}
