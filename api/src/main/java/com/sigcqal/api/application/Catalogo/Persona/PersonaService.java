package com.sigcqal.api.application.Catalogo.Persona;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Persona.Mapper.PersonaMapper;
import com.sigcqal.api.web.Catalogo.Persona.Dto.PersonaDTO;
import com.sigcqal.api.web.Catalogo.Persona.Dto.PersonaRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonaService {

    private final PersonaRepositoryPort port;
    private final PersonaMapper mapper;

    @Transactional
    public PersonaDTO guardar(PersonaRequestDTO request) {
       
     Long direccionId = request.getIdDireccion() != null ? request.getIdDireccion().longValue() : null;
        Long tipoPersonaId = request.getIdTipoPersona() != null ? request.getIdTipoPersona().longValue() : null;
        
        Persona persona = Persona.builder()
                .idDireccion(direccionId)
                .nombre(request.getNombre())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .curp(request.getCurp())
                .telefono(request.getTelefono())
                .comunidad(request.getComunidad())
                .rfc(request.getRfc())
                .rec(request.getRec())
                .identificacionOficial(request.getIdentificacionOficial())
                .telefonoFijo(request.getTelefonoFijo())
                .numeroIdFolio(request.getNumeroIdFolio())
                .correo(request.getCorreo())
                .idTipoPersona(tipoPersonaId)
                .tipoIdentificacion(request.getTipoIdentificacion())
                .build();

        return mapper.toResponse(port.save(persona));
    }

    public PersonaDTO obtenerPorId(Long id) {
        return port.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new InvalidRequestException(
                        "Persona no encontrada con id: " + id));
    }

    public List<PersonaDTO> obtenerTodas() {
        return port.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PersonaDTO actualizar(Long id, PersonaRequestDTO request) {
        Persona existente = port.findById(id)
                .orElseThrow(() -> new InvalidRequestException(
                        "Persona no encontrada con id: " + id));

        Long direccionId = request.getIdDireccion() != null ? request.getIdDireccion().longValue() : null;
        Long tipoPersonaId = request.getIdTipoPersona() != null ? request.getIdTipoPersona().longValue() : null;
        existente.setIdDireccion(direccionId);
        existente.setNombre(request.getNombre());
        existente.setApellidoPaterno(request.getApellidoPaterno());
        existente.setApellidoMaterno(request.getApellidoMaterno());
        existente.setCurp(request.getCurp());
        existente.setTelefono(request.getTelefono());
        existente.setComunidad(request.getComunidad());
        existente.setRfc(request.getRfc());
        existente.setRec(request.getRec());
        existente.setIdentificacionOficial(request.getIdentificacionOficial());
        existente.setTelefonoFijo(request.getTelefonoFijo());
        existente.setNumeroIdFolio(request.getNumeroIdFolio());
        existente.setCorreo(request.getCorreo());
        existente.setIdTipoPersona(tipoPersonaId);

        return mapper.toResponse(port.save(existente));
    }

    // Método para uso interno de otros servicios (dominio puro)
    @Transactional
    public Persona guardarPersonaDeDominio(Persona persona) {
        // Usamos el port para guardar el modelo de dominio directamente
        Persona personaGuardada = port.save(persona);
        return personaGuardada;
    }

}