package com.sigcqal.api.web.Catalogo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.domain.Catalogo.Estado;
import com.sigcqal.api.domain.Catalogo.EstadoRepositoryPort;

@RestController
@RequestMapping("/catalogos/estados")
public class EstadoController {

    private final EstadoRepositoryPort repositoryPort;

    public EstadoController(EstadoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @GetMapping
    public List<EstadoDTO> listar() {
        return repositoryPort.ListAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private EstadoDTO mapToResponse(Estado dom) {
        EstadoDTO res = new EstadoDTO();
        res.setNombreEstado(dom.getNombreEstado());
        return res;
    }
}