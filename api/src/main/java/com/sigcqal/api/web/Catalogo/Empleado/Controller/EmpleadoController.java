package com.sigcqal.api.web.Catalogo.Empleado.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.application.Catalogo.Empleado.EmpleadoService;
import com.sigcqal.api.web.Catalogo.Empleado.Dto.EmpleadoDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalogos/empleados")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    @GetMapping
    @Operation(summary = "Listar empleados")
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleados() {
        return ResponseEntity.ok(empleadoService.obtenerEmpleados());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por id")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleado(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.obtenerEmpleado(id));
    }
}
