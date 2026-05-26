package com.sigcqal.api.web.Catalogo.ControlFoliosConfig.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.ControlFoliosConfig.ControlFoliosConfigService;
import com.sigcqal.api.web.Catalogo.ControlFoliosConfig.Dto.ControlFoliosConfigDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/control-folios-config")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ControlFoliosConfigController {
    private final ControlFoliosConfigService controlFoliosConfigService;

    @GetMapping
    @Operation(summary = "Listar control folios config")
    public ResponseEntity<List<ControlFoliosConfigDTO>> listarControlFoliosConfig() {
        return ResponseEntity.ok(controlFoliosConfigService.obtenerControlFoliosConfigs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener control folios config por id")
    public ResponseEntity<ControlFoliosConfigDTO> obtenerControlFoliosConfig(@PathVariable Long id) {
        return ResponseEntity.ok(controlFoliosConfigService.obtenerControlFoliosConfig(id));
    }
}
