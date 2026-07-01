package com.sigcqal.api.web.Catalogo.Personal.Controller;

import com.sigcqal.api.application.Catalogo.Personal.PersonalService;
import com.sigcqal.api.domain.Catalogo.Personal.Model.Personal;
import com.sigcqal.api.web.Catalogo.Personal.Dto.PersonalRequestDTO;
import com.sigcqal.api.web.Catalogo.Personal.Dto.PersonalDTO;
import com.sigcqal.api.web.Catalogo.Personal.Dto.PersonalDetailDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    @PostMapping
   // En tu PersonalController.java
    public ResponseEntity<Void> create(@RequestBody PersonalRequestDTO request) {
        personalService.registrarPersonal(request); // Llama al método que definiste
        return ResponseEntity.ok().build(); // Devuelve 200 OK sin cuerpo o con el creado
    }

    @GetMapping
    public ResponseEntity<List<PersonalDTO>> getAll() {
        return ResponseEntity.ok(personalService.getAllPersonal().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalDetailDTO> getById(@PathVariable Long id) {
        Personal personal = personalService.findById(id);
        
        var p = personal.getPersona();
        var d = p.getDireccion(); // ¡Ahora esto traerá los datos desde el Service!

        // Construimos el DTO de detalle
        var dto = PersonalDetailDTO.builder()
                .idPersonal(personal.getIdPersonal())
                .idPersona(p.getId())
                .nombre(p.getNombre())
                .apellidoPaterno(p.getApellidoPaterno())
                .apellidoMaterno(p.getApellidoMaterno())
                    .curp(p.getCurp())
                    .telefono(p.getTelefono())
                    .comunidad(p.getComunidad())
                    .rfc(p.getRfc())
                    .rec(p.getRec())
                    .identificacionOficial(p.getIdentificacionOficial())
                    .telefonoFijo(p.getTelefonoFijo())
                    .numeroIdFolio(p.getNumeroIdFolio())
                    .correo(p.getCorreo())
                    .idTipoPersona(p.getIdTipoPersona())
                    .tipoIdentificacion(p.getTipoIdentificacion())
                    .activo(personal.getActivo())
                    .calle(d != null ? d.getCalle() : "")
                    .numExt(d != null ? d.getNumExt() : "" )
                    .colonia(d != null ? d.getColonia() : "")
                    .cp(d != null ? d.getCp() : "")
                    .idMunicipio(d != null ? d.getIdMunicipio() : null)
                    .idEstado(d != null ? d.getIdEstado() : null)

                    .build();
                return ResponseEntity.ok(dto);
        }


    @PutMapping("/{id}")
    public ResponseEntity<PersonalDTO> update(@PathVariable Long id, @RequestBody PersonalRequestDTO request) {
        Personal updated = personalService.updatePersonal(id, request);
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
