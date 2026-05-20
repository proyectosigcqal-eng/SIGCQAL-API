package com.sigcqal.api.application.ModuloCorrespondencia.OficioContestacionExterna;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Model.OficioContestacionExterna;
import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Port.OficioContestacionExternaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Mapper.OficioContestacionExternaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.OficioContestacionExterna.Dto.OficioContestacionExternaDTOs;

@ExtendWith(MockitoExtension.class)
class OficioContestacionExternaServiceTest {
    @Mock
    private OficioContestacionExternaRepositoryPort repositoryPort;

    @Mock
    private OficioContestacionExternaMapper mapper;

    @InjectMocks
    private OficioContestacionExternaService service;

    @Test
    void buscarPorCorrespondencia_idInvalido_lanzaInvalidRequest() {
        assertThatThrownBy(() -> service.buscarPorCorrespondencia(null)).isInstanceOf(InvalidRequestException.class);
        assertThatThrownBy(() -> service.buscarPorCorrespondencia(0L)).isInstanceOf(InvalidRequestException.class);
        assertThatThrownBy(() -> service.buscarPorCorrespondencia(-1L)).isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void buscarPorCorrespondencia_noExiste_retornaOptionalVacio() {
        when(repositoryPort.buscarPorCorrespondencia(8L)).thenReturn(Optional.empty());

        var res = service.buscarPorCorrespondencia(8L);

        assertThat(res).isEmpty();
    }

    @Test
    void buscarPorCorrespondencia_existe_mapea() {
        OficioContestacionExterna dom = new OficioContestacionExterna();
        dom.setIdOficioContestacion(1L);
        dom.setIdCorrespondencia(8L);

        OficioContestacionExternaDTOs.Response dto = new OficioContestacionExternaDTOs.Response();
        dto.setIdOficioContestacion(1L);
        dto.setIdCorrespondencia(8L);

        when(repositoryPort.buscarPorCorrespondencia(8L)).thenReturn(Optional.of(dom));
        when(mapper.toResponse(dom)).thenReturn(dto);

        var res = service.buscarPorCorrespondencia(8L);

        assertThat(res).isPresent();
        assertThat(res.get().getIdOficioContestacion()).isEqualTo(1L);
        assertThat(res.get().getIdCorrespondencia()).isEqualTo(8L);
    }
}

