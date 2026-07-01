package com.sigcqal.api.web.ModuloAreaSustantiva.BandejaRepresentacionLegal.Controller;

import com.sigcqal.api.application.ModuloAreaSustantiva.BandejaRepresentacionLegal.BandejaRepresentacionService;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Model.RepresentacionBandeja;
import com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Mapper.BandejaRepresentacionMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaRepresentacionLegal.Dto.BandejaRepresentacionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tramites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BandejaRepresentacionController {

    private final BandejaRepresentacionService service;
    private final BandejaRepresentacionMapper mapper;

    @GetMapping("/bandeja-representacion-legal")
    public ResponseEntity<List<BandejaRepresentacionResponseDto>> obtenerBandeja(
        @RequestParam(required = false, defaultValue = "") String search,
        @RequestParam(required = false, defaultValue = "") String estatus
    ) {
        List<RepresentacionBandeja> bandeja = service.obtenerBandeja(
            search.isEmpty() ? null : search,
            estatus.isEmpty() ? null : estatus
        );

        List<BandejaRepresentacionResponseDto> response = bandeja.stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
