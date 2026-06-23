package com.sigcqal.api.web.ModuloAreaSustantiva.PlantillaQuejaAri.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.sigcqal.api.application.ModuloAreaSustantiva.PlantillaQuejaAri.PlantillaQuejaAriService;
import com.sigcqal.api.web.ModuloAreaSustantiva.PlantillaQuejaAri.Dto.PlantillaQuejaAriDTO;

@RestController
@RequestMapping("/catalogos/plantillas-queja")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class PlantillaQuejaAriController {

    private final PlantillaQuejaAriService plantillaService;

    @GetMapping("/listar")
    public ResponseEntity<List<PlantillaQuejaAriDTO>> listarPlantillas() {
        return ResponseEntity.ok(plantillaService.listarPlantillas());
    }

    
    
}