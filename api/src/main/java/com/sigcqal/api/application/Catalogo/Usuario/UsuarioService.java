package com.sigcqal.api.application.Catalogo.Usuario;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Usuario.Model.Usuario;
import com.sigcqal.api.domain.Catalogo.Usuario.Port.UsuarioRepositoryPort;
import com.sigcqal.api.web.Catalogo.Usuario.Dto.UsuarioDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepositoryPort repositoryPort;

    public List<UsuarioDTO> obtenerUsuarios() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    public UsuarioDTO obtenerUsuario(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Usuario usuario = repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));

        return mapToResponse(usuario);
    }

    private UsuarioDTO mapToResponse(Usuario dom) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(dom.getId());
        dto.setIdPersona(dom.getIdPersona());
        dto.setIdRol(dom.getIdRol());
        dto.setIdArea(dom.getIdArea());
        dto.setNombreArea(dom.getNombreArea());
        dto.setUsuarioLogin(dom.getUsuarioLogin());
        dto.setCorreoElectronico(dom.getCorreoElectronico());
        return dto;
    }
}
