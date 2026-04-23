package com.sigcqal.api.web.ModuloCorrespondencia.PlantillaMemorandum.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.sigcqal.api.application.ModuloCorrespondencia.PlantillaMemorandum.PlantillaMemorandumService;
import com.sigcqal.api.web.ModuloCorrespondencia.PlantillaMemorandum.Dto.PlantillaMemorandumDTO;

@RestController
@RequestMapping("/catalogos/plantillas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class PlantillaMemorandumController {

    private final PlantillaMemorandumService plantillaService;

    @GetMapping("/listar")
    public ResponseEntity<List<PlantillaMemorandumDTO>> listarPlantillas() {
        return ResponseEntity.ok(plantillaService.listarPlantillas());
    }
}
