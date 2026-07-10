package com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.sigcqal.api.application.ModuloAreaSustantiva.ControlPlazosAutoridad.ControlPlazosAutoridadService;
import com.sigcqal.api.test.support.WebMvcTestSecurityConfig;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.RegistroInformeAutoridadResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.SemaforoAutoridadResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.SemaforoEstadoEnum;

@WebMvcTest(controllers = ControlPlazosAutoridadController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(WebMvcTestSecurityConfig.class)
class ControlPlazosAutoridadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControlPlazosAutoridadService service;

    @Test
    void obtenerSemaforo_ok() throws Exception {
        when(service.obtenerSemaforo(1L)).thenReturn(SemaforoAutoridadResponseDTO.builder()
                .expedienteId(1L)
                .folioGobierno("FOL-1")
                .estado(SemaforoEstadoEnum.VERDE)
                .diasHabilesRestantes(3)
                .vencido(false)
                .build());

        mockMvc.perform(get("/api/v1/expedientes/1/plazo-autoridad/semaforo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expedienteId").value(1))
                .andExpect(jsonPath("$.estado").value("VERDE"))
                .andExpect(jsonPath("$.diasHabilesRestantes").value(3));
    }

    @Test
    void registrarInforme_multipart_ok() throws Exception {
        when(service.registrarInforme(eq(1L), any(), any()))
                .thenReturn(RegistroInformeAutoridadResponseDTO.builder()
                        .expedienteId(1L)
                        .folioGobierno("FOL-1")
                        .numeroOficioRespuesta("OF-1")
                        .fojas(1)
                        .rutaPdfInforme("/api/files/expedientes/a.pdf")
                        .build());

        MockMultipartFile requestPart = new MockMultipartFile(
                "request",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                "{\"numeroOficioRespuesta\":\"OF-1\",\"fojas\":1,\"fechaRecepcion\":\"2026-06-12\"}".getBytes());
        MockMultipartFile pdf = new MockMultipartFile("pdf", "doc.pdf", "application/pdf", "x".getBytes());

        mockMvc.perform(multipart("/api/v1/expedientes/1/plazo-autoridad/registrar-informe")
                .file(requestPart)
                .file(pdf))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expedienteId").value(1))
                .andExpect(jsonPath("$.numeroOficioRespuesta").value("OF-1"));
    }
}

