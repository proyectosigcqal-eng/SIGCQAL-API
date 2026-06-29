package com.sigcqal.api.application.Catalogo.Personal;

import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Personal.Model.Personal;
import com.sigcqal.api.domain.Catalogo.Personal.Port.PersonalRepositoryPort;
import com.sigcqal.api.application.Catalogo.Persona.PersonaService; // Asumiendo que este es el que ya tienes
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalService {

    private final PersonalRepositoryPort personalRepository;
    private final PersonaService personaService;

    @Transactional
    public Personal registerPersonal(Personal personal) {
        Persona personaGuardada = personaService.guardarPersonaDeDominio(personal.getPersona());
        personal.setPersona(personaGuardada);
        return personalRepository.save(personal);
    }

    // LISTAR
    public List<Personal> getAllPersonal() {
        return personalRepository.findAll();
    }

    // BUSCAR POR ID
    public Optional<Personal> getPersonalById(Long id) {
        return personalRepository.findById(id);
    }

    // ACTUALIZAR (PUT)
    @Transactional
    public Personal updatePersonal(Long id, Personal personalActualizado) {
        // 1. Verificamos si existe el registro de Personal
        Personal personalExistente = personalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Personal no encontrado con id: " + id));

        // 2. Actualizamos la Persona asociada
        // Usamos el id de la persona que ya estaba asociada al personal
        Persona personaAActualizar = personalActualizado.getPersona();
        personaAActualizar.setId(personalExistente.getPersona().getId());
        
        Persona personaActualizada = personaService.guardarPersonaDeDominio(personaAActualizar);

        // 3. Actualizamos los campos de Personal
        personalExistente.setPersona(personaActualizada);
        personalExistente.setActivo(personalActualizado.getActivo());
        // Puedes agregar más campos si los tuviste en tu modelo

        return personalRepository.save(personalExistente);
    }

    // ELIMINAR
    @Transactional
    public void deletePersonal(Long id) {
        // Antes de eliminar, podrías verificar si existe
        if (!personalRepository.findById(id).isPresent()) {
            throw new RuntimeException("No se puede eliminar, el registro no existe");
        }
        personalRepository.deleteById(id);
    }
}
