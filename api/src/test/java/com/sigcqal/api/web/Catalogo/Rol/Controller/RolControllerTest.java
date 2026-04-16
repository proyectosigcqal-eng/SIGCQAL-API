package com.sigcqal.api.web.Catalogo.Rol.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.sigcqal.api.application.Catalogo.Rol.RolService;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.web.Catalogo.Rol.Dto.RolDTO;
import com.sigcqal.api.web.exception.ApiExceptionHandler;

@WebMvcTest(controllers = RolController.class)
@Import(ApiExceptionHandler.class)
class RolControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Test
    void listarRoles_ok() throws Exception {
        when(rolService.obtenerRoles()).thenReturn(List.of(new RolDTO(1L, "Rol 1", "Desc 1")));

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Rol 1"))
                .andExpect(jsonPath("$[0].descripcion").value("Desc 1"));
    }

    @Test
    void obtenerRol_ok() throws Exception {
        when(rolService.obtenerRol(2L)).thenReturn(new RolDTO(2L, "Rol 2", "Desc 2"));

        mockMvc.perform(get("/api/roles/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Rol 2"))
                .andExpect(jsonPath("$.descripcion").value("Desc 2"));
    }

    @Test
    void obtenerRol_noExiste_devuelve404() throws Exception {
        when(rolService.obtenerRol(99L)).thenThrow(new ResourceNotFoundException("Rol", 99L));

        mockMvc.perform(get("/api/roles/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso no encontrado"));
    }
}
