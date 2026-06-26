package com.sigcqal.api.application.Catalogo.Asesor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Asesor.Model.Asesor;
import com.sigcqal.api.domain.Catalogo.Asesor.Port.AsesorRepositoryPort;
import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.web.Admin.Dto.AsesorAdminRequestDTO;
import com.sigcqal.api.web.Catalogo.Asesor.Dto.AsesorDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsesorService {
    private final AsesorRepositoryPort repositoryPort;
    private final PersonaRepositoryPort personaRepositoryPort;

   public List<AsesorDTO> obtenerAsesores() {
    return repositoryPort.findAll()
            .stream()
            .filter(a -> Boolean.TRUE.equals(a.getActivo())) // ✅ solo activos
            .map(this::mapToResponse)
            .toList();
}

   private AsesorDTO mapToResponse(Asesor domain) {
    AsesorDTO dto = new AsesorDTO();
    dto.setIdAsesor(domain.getIdAsesor());
    dto.setIdPersona(domain.getIdPersona());
    dto.setEspecialidad(domain.getEspecialidad());
    dto.setCargaActual(domain.getCargaActual());
    dto.setActivo(domain.getActivo() != null ? domain.getActivo() : true);
    dto.setUltimaAsignacionAt(
        domain.getUltimaAsignacionAt() != null
            ? domain.getUltimaAsignacionAt().toString() : null);

    // ✅ Todos los campos de persona dentro del ifPresent
    personaRepositoryPort.findById(domain.getIdPersona()).ifPresent(p -> {
        System.out.println(">>> apellidoPaterno a setear: " + p.getApellidoPaterno());
        dto.setNombre(p.getNombre());
        dto.setApellidoPaterno(p.getApellidoPaterno());   // ✅
        dto.setApellidoMaterno(p.getApellidoMaterno());   // ✅
        dto.setCorreo(p.getCorreo());
        dto.setTelefono(p.getTelefono());                 // ✅
        dto.setRfc(p.getRfc());                           // ✅
        dto.setNombreCompleto(String.join(" ",
            p.getNombre()          != null ? p.getNombre()          : "",
            p.getApellidoPaterno() != null ? p.getApellidoPaterno() : "",
            p.getApellidoMaterno() != null ? p.getApellidoMaterno() : ""
        ).trim());
        System.out.println(">>> dto apellidoPaterno después: " + dto.getApellidoPaterno());
    });
        return dto;
    }

    private String getNombrePersona(Long idPersona) {
        if (idPersona == null) {
            return null;
        }
        return personaRepositoryPort.findById(idPersona).map(persona -> persona.getNombre()).orElse(null);
    }

    // Agrega estos métodos al AsesorService existente — también necesita PersonaRepositoryPort
    
public AsesorDTO crearAsesor(AsesorAdminRequestDTO request) {
    if (request.getNombre() == null || request.getApellidoPaterno() == null) {
        throw new InvalidRequestException("Nombre y apellido paterno son obligatorios.");
    }

    // 1. Crear persona
    Persona persona = new Persona();
    persona.setNombre(request.getNombre());
    persona.setApellidoPaterno(request.getApellidoPaterno());
    persona.setApellidoMaterno(request.getApellidoMaterno());
    persona.setCorreo(request.getCorreo());
    persona.setTelefono(request.getTelefono());
    persona.setRfc(request.getRfc());
    Long idPersona = personaRepositoryPort.save(persona).getId();

    // 2. Crear asesor
    Asesor asesor = new Asesor();
    asesor.setIdPersona(idPersona);
    asesor.setEspecialidad(request.getEspecialidad());
    asesor.setCargaActual(0);

    return mapToResponse(repositoryPort.save(asesor));
}

public AsesorDTO actualizarAsesor(Long id, AsesorAdminRequestDTO request) {
    // 1. Buscar el asesor para obtener su idPersona
    Asesor asesor = repositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asesor", id));

    // 2. Actualizar persona
    personaRepositoryPort.findById(asesor.getIdPersona()).ifPresent(persona -> {
        if (request.getNombre()          != null) persona.setNombre(request.getNombre());
        if (request.getApellidoPaterno() != null) persona.setApellidoPaterno(request.getApellidoPaterno());
        if (request.getApellidoMaterno() != null) persona.setApellidoMaterno(request.getApellidoMaterno());
        if (request.getCorreo()          != null) persona.setCorreo(request.getCorreo());
        if (request.getTelefono()        != null) persona.setTelefono(request.getTelefono());
        if (request.getRfc()             != null) persona.setRfc(request.getRfc());
        personaRepositoryPort.save(persona);
    });

    // 3. Actualizar especialidad del asesor
    Asesor actualizado = new Asesor();
    actualizado.setEspecialidad(request.getEspecialidad());
    repositoryPort.actualizar(id, actualizado);

    // 4. Retornar el asesor actualizado
    return mapToResponse(repositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asesor", id)));
}

public void darBajaAsesor(Long id) {
    repositoryPort.darBaja(id);
}
public List<AsesorDTO> obtenerTodos() {
    return repositoryPort.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
}

}
