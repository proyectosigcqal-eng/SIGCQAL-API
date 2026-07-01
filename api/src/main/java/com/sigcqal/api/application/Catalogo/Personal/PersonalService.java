package com.sigcqal.api.application.Catalogo.Personal;

import com.sigcqal.api.domain.Catalogo.Direccion.Model.Direccion;
import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Personal.Model.Personal;
import com.sigcqal.api.domain.Catalogo.Personal.Port.PersonalRepositoryPort;
import com.sigcqal.api.web.Catalogo.Personal.Dto.PersonalRequestDTO; // Asumiendo que este es el que ya tienes
import com.sigcqal.api.domain.Catalogo.Direccion.Port.DireccionRepositoryPort; // Asegúrate de tener este puerto
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalService {

    private final PersonalRepositoryPort personalRepository;
    private final PersonaRepositoryPort personaRepositoryPort;
    private final DireccionRepositoryPort direccionRepository; // Asegúrate de tener este puerto

    @Transactional
        public void registrarPersonal(PersonalRequestDTO dto) {
            // 1. Mapear DTO a un Modelo de Dominio (Personal)
            Direccion dirDominio = Direccion.builder()
            .id(null)
                .calle(dto.getCalle())
                .numExt(dto.getNumExt())
                .colonia(dto.getColonia())
                .cp(dto.getCp())
                .idEstado(dto.getIdEstado())
                .idMunicipio(dto.getIdMunicipio())
                .build();


            Personal personalDominio = Personal.builder()
                .activo(true)
                .persona(Persona.builder()
                    .nombre(dto.getNombre())
                    .apellidoPaterno(dto.getApellidoPaterno())
                    .apellidoMaterno(dto.getApellidoMaterno())
                    .curp(dto.getCurp())
                    .idDireccion(dto.getIdDireccion())
                    .correo(dto.getCorreo())
                    .rfc(dto.getRfc())
                    .telefono(dto.getTelefono())
                    .telefonoFijo(dto.getTelefonoFijo())
                    .build())
                .build();

            // 2. El puerto ahora acepta el modelo de dominio
            personalRepository.save(personalDominio, dirDominio); 
        }

    // LISTAR
    public List<Personal> getAllPersonal() {
        // Asumiendo que tu repositorio tiene un método findAll()
        return personalRepository.findAll(); 
    }
   
    public Personal findById(Long id) {
        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        Long idDir = personal.getPersona().getIdDireccion();
        
        if (idDir != null) {
            // Buscamos la dirección
            Optional<Direccion> dirCompleta = direccionRepository.findById(idDir);
            
            if (dirCompleta.isPresent()) {
                personal.getPersona().setDireccion(dirCompleta.get());
            } else {
                System.out.println("ADVERTENCIA: Se encontró el ID de dirección " + idDir + " pero no existe en BD.");
            }
        }
        return personal;
    }

    // BUSCAR POR ID
    public Optional<Personal> getPersonalById(Long id) {
        return personalRepository.findById(id);
    }

    // ACTUALIZAR (PUT)
    @Transactional
    public Personal updatePersonal(Long id, PersonalRequestDTO dto) {
        // 1. Obtener el Personal existente
        Personal personalExistente = personalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No encontrado"));
            
        // 2. Mapear los cambios al objeto de dominio (Persona)
        Persona p = personalExistente.getPersona();
        p.setNombre(dto.getNombre());
        p.setApellidoPaterno(dto.getApellidoPaterno());
        p.setApellidoMaterno(dto.getApellidoMaterno());
        p.setComunidad(dto.getComunidad());
        p.setCorreo(dto.getCorreo());
        p.setCurp(dto.getCurp());
        p.setIdTipoPersona(dto.getIdTipoPersona());
        p.setRfc(dto.getRfc());
        p.setTelefono(dto.getTelefono());
        p.setTelefonoFijo(dto.getTelefonoFijo());

        // 3. ACTUALIZAR LA DIRECCIÓN
        // OJO: Si personalExistente se obtiene desde el repositorio (que devuelve Entidades JPA 
        // convertidas a dominio por el Mapper), necesitamos asegurarnos de que el Mapper 
        // incluya la entidad Direccion dentro de la Persona.
        
        // Si tu arquitectura te permite acceder a la entidad de dirección, haz esto:
        Direccion direccionActualizada = Direccion.builder()
                .id(p.getIdDireccion()) // Es vital pasar el ID existente
                .calle(dto.getCalle())
                .numExt(dto.getNumExt())
                .colonia(dto.getColonia())
                .cp(dto.getCp())
                .idEstado(dto.getIdEstado())
                .idMunicipio(dto.getIdMunicipio())
                .build();
                      // 4. Guardar los cambios finales del personal
    return personalRepository.save(personalExistente, direccionActualizada);

                }



    // ELIMINAR
        @Transactional
        public void deletePersonal(Long id) {
            // 1. Buscamos el personal para obtener el ID de la persona asociada
            Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

            Long personaId = personal.getPersona().getId();

            // 2. Borramos primero el Personal
            personalRepository.deleteById(id);

            // 3. Borramos después la Persona
            // Necesitas inyectar el PersonaRepositoryPort o usar un método del PersonaService
            personaRepositoryPort.deleteById(personaId);
        }
}
