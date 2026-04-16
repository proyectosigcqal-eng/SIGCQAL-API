package com.sigcqal.api.web.Catalogo.Area.Controller;

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

import com.sigcqal.api.application.Catalogo.Area.AreaService;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.web.Catalogo.Area.Dto.AreaDTO;
import com.sigcqal.api.web.exception.ApiExceptionHandler;

@WebMvcTest(controllers = AreaController.class)
@Import(ApiExceptionHandler.class)
class AreaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AreaService areaService;

    @Test
    void listarAreas_ok() throws Exception {
        when(areaService.obtenerAreas()).thenReturn(List.of(new AreaDTO(1L, "Area 1", "Desc 1")));

        mockMvc.perform(get("/api/areas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Area 1"))
                .andExpect(jsonPath("$[0].descripcion").value("Desc 1"));
    }

    @Test
    void obtenerArea_ok() throws Exception {
        when(areaService.obtenerArea(2L)).thenReturn(new AreaDTO(2L, "Area 2", "Desc 2"));

        mockMvc.perform(get("/api/areas/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Area 2"))
                .andExpect(jsonPath("$.descripcion").value("Desc 2"));
    }

    @Test
    void obtenerArea_noExiste_devuelve404() throws Exception {
        when(areaService.obtenerArea(99L)).thenThrow(new ResourceNotFoundException("Área", 99L));

        mockMvc.perform(get("/api/areas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso no encontrado"));
    }
}
