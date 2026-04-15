package com.sigcqal.api.application.Catalogo.Area;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Area.Model.Area;
import com.sigcqal.api.domain.Catalogo.Area.Port.AreaRepositoryPort;

@ExtendWith(MockitoExtension.class)
class AreaServiceTest {
    @Mock
    private AreaRepositoryPort repositoryPort;

    @InjectMocks
    private AreaService service;

    @Test
    void obtenerAreas_mapeaLista() {
        Area a1 = new Area();
        a1.setId(1L);
        a1.setNombre("Area 1");
        a1.setDescripcion("Desc 1");

        when(repositoryPort.findAll()).thenReturn(List.of(a1));

        var res = service.obtenerAreas();

        assertThat(res).hasSize(1);
        assertThat(res.getFirst().getId()).isEqualTo(1L);
        assertThat(res.getFirst().getNombre()).isEqualTo("Area 1");
        assertThat(res.getFirst().getDescripcion()).isEqualTo("Desc 1");
    }

    @Test
    void obtenerArea_idInvalido_lanzaInvalidRequest() {
        assertThatThrownBy(() -> service.obtenerArea(0L)).isInstanceOf(InvalidRequestException.class);
        assertThatThrownBy(() -> service.obtenerArea(-1L)).isInstanceOf(InvalidRequestException.class);
        assertThatThrownBy(() -> service.obtenerArea(null)).isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void obtenerArea_noExiste_lanzaNotFound() {
        when(repositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtenerArea(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void obtenerArea_existe_mapea() {
        Area a1 = new Area();
        a1.setId(2L);
        a1.setNombre("Area 2");
        a1.setDescripcion("Desc 2");

        when(repositoryPort.findById(2L)).thenReturn(Optional.of(a1));

        var res = service.obtenerArea(2L);

        assertThat(res.getId()).isEqualTo(2L);
        assertThat(res.getNombre()).isEqualTo("Area 2");
        assertThat(res.getDescripcion()).isEqualTo("Desc 2");
    }
}
