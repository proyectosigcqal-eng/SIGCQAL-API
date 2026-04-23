package com.sigcqal.api.web.Catalogo.Estatus.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Estatus.EstatusService;
import com.sigcqal.api.web.Catalogo.Estatus.Dto.EstatusDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/catalogos/estatus")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class EstatusController {

    private final EstatusService estatusService;

    @GetMapping
    public ResponseEntity<List<EstatusDTO>> listarEstatus() {
        return ResponseEntity.ok(estatusService.obtenerEstatus());
    }
    
    
}