package com.sigcqal.api.web.Catalogo.Personal.Controller;

import com.sigcqal.api.application.Catalogo.Personal.PersonalService;
import com.sigcqal.api.domain.Catalogo.Personal.Model.Personal;
import com.sigcqal.api.web.Catalogo.Personal.Dto.PersonalRequestDTO;
import com.sigcqal.api.web.Catalogo.Personal.Dto.PersonalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    @PostMapping
    public ResponseEntity<PersonalDTO> create(@RequestBody PersonalRequestDTO request) {
        // Aquí convertirías el DTO a Modelo (puedes usar un mapper aquí)
        Personal personal = mapToDomain(request);
        Personal saved = personalService.registerPersonal(personal);
        return ResponseEntity.ok(mapToResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<PersonalDTO>> getAll() {
        return ResponseEntity.ok(personalService.getAllPersonal().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonalDTO> update(@PathVariable Long id, @RequestBody PersonalRequestDTO request) {
        Personal personal = mapToDomain(request);
        Personal updated = personalService.updatePersonal(id, personal);
        return ResponseEntity.ok(mapToResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personalService.deletePersonal(id);
        return ResponseEntity.noContent().build();
    }

    // Helpers de mapeo (puedes mover esto a un PersonalWebMapper)
    private Personal mapToDomain(PersonalRequestDTO dto) {
        return Personal.builder()
                .activo(dto.getActivo())
                .persona(com.sigcqal.api.domain.Catalogo.Persona.Model.Persona.builder()
                        .nombre(dto.getNombre())
                        .apellidoPaterno(dto.getApellidoPaterno())
                        .apellidoMaterno(dto.getApellidoMaterno())
                        .curp(dto.getCurp())
                        .telefono(dto.getTelefono())
                        .comunidad(dto.getComunidad())
                        .rfc(dto.getRfc())
                        .rec(dto.getRec())
                        .identificacionOficial(dto.getIdentificacionOficial())
                        .telefonoFijo(dto.getTelefonoFijo())
                        .numeroIdFolio(dto.getNumeroIdFolio())
                        .correo(dto.getCorreo())
                        .idTipoPersona(dto.getIdTipoPersona())
                        .tipoIdentificacion(dto.getTipoIdentificacion())
                        .idDireccion(dto.getIdDireccion())
                        .build())
                .build();
    }

    private PersonalDTO mapToResponse(Personal domain) {
        return PersonalDTO.builder()
                .idPersonal(domain.getIdPersonal())
                .idPersona(domain.getPersona().getId())
                .nombreCompleto(domain.getPersona().getNombre() + " " + domain.getPersona().getApellidoPaterno())
                .activo(domain.getActivo())
                .build();
    }
}
