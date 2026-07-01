package com.sigcqal.api.application.ModuloAreaSustantiva.Expediente;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Contribuyente;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port.ContribuyenteRepositoryPort;
import com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO.ContribuyenteBusquedaDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContribuyenteBusquedaService {

    private final PersonaRepositoryPort personaPort;
    private final ContribuyenteRepositoryPort contribuyentePort;

    private static final int MIN_LARGO_BUSQUEDA = 3;

    public List<ContribuyenteBusquedaDTO> buscar(String texto) {
        if (texto == null || texto.trim().length() < MIN_LARGO_BUSQUEDA) {
            throw new InvalidRequestException(
                    "Ingresa al menos " + MIN_LARGO_BUSQUEDA + " caracteres para buscar.");
        }

        List<Persona> personas = personaPort.buscarPorNombreORfc(texto.trim());

        return personas.stream()
                .map(this::toBusquedaDTO)
                .collect(Collectors.toList());
    }

    private ContribuyenteBusquedaDTO toBusquedaDTO(Persona persona) {
        // ¿Esta persona ya tiene registro en contribuyentes?
        Contribuyente contribuyente = contribuyentePort
                .findByIdPersona(persona.getId())
                .orElse(null);

        return ContribuyenteBusquedaDTO.builder()
                .idPersona(persona.getId())
                .idContribuyente(contribuyente != null ? contribuyente.getId() : null)
                .nombre(persona.getNombre())
                .apellidoPaterno(persona.getApellidoPaterno())
                .apellidoMaterno(persona.getApellidoMaterno())
                .rfc(persona.getRfc())
                .curp(persona.getCurp())
                .telefono(persona.getTelefono())
                .telefonoFijo(persona.getTelefonoFijo())
                .correo(persona.getCorreo())
                .comunidad(persona.getComunidad())
                .rec(persona.getRec())
                .identificacionOficial(persona.getIdentificacionOficial())
                .numeroIdFolio(persona.getNumeroIdFolio())
                .tipoIdentificacion(persona.getTipoIdentificacion())
                .idDireccion(persona.getIdDireccion())
                .idTipoPersona(persona.getIdTipoPersona())
                .nombreTipoPersona(persona.getNomreTipoPersona())
                .fechaRegistroSistema(contribuyente != null ? contribuyente.getFechaRegistroSistema() : null)
                .observacionesInternas(contribuyente != null ? contribuyente.getObservacionesInternas() : null)
                .build();
    }
}