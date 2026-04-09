package com.sigcqal.api.web.Catalogo.Municipio.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.sigcqal.api.application.Catalogo.Municipio.MunicipioService;
import com.sigcqal.api.web.Catalogo.Municipio.Dto.MunicipioDTO;

@RestController
@RequestMapping("/catalogos/municipios")
@RequiredArgsConstructor
public class MunicipioController {

    private final MunicipioService municipioService;

    @GetMapping
    public ResponseEntity<List<MunicipioDTO>> listarMunicipios() {
        return ResponseEntity.ok(municipioService.obtenerMunicipios());
        
    }
}