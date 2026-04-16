package com.sigcqal.api.application.Catalogo.Rol;

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
import com.sigcqal.api.domain.Catalogo.Rol.Model.Rol;
import com.sigcqal.api.domain.Catalogo.Rol.Port.RolRepositoryPort;

@ExtendWith(MockitoExtension.class)
class RolServiceTest {
    @Mock
    private RolRepositoryPort repositoryPort;

    @InjectMocks
    private RolService service;

    @Test
    void obtenerRoles_mapeaLista() {
        Rol r1 = new Rol();
        r1.setId(1L);
        r1.setNombre("Rol 1");
        r1.setDescripcion("Desc 1");

        when(repositoryPort.findAll()).thenReturn(List.of(r1));

        var res = service.obtenerRoles();

        assertThat(res).hasSize(1);
        assertThat(res.getFirst().getId()).isEqualTo(1L);
        assertThat(res.getFirst().getNombre()).isEqualTo("Rol 1");
        assertThat(res.getFirst().getDescripcion()).isEqualTo("Desc 1");
    }

    @Test
    void obtenerRol_idInvalido_lanzaInvalidRequest() {
        assertThatThrownBy(() -> service.obtenerRol(0L)).isInstanceOf(InvalidRequestException.class);
        assertThatThrownBy(() -> service.obtenerRol(-1L)).isInstanceOf(InvalidRequestException.class);
        assertThatThrownBy(() -> service.obtenerRol(null)).isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void obtenerRol_noExiste_lanzaNotFound() {
        when(repositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtenerRol(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void obtenerRol_existe_mapea() {
        Rol r1 = new Rol();
        r1.setId(2L);
        r1.setNombre("Rol 2");
        r1.setDescripcion("Desc 2");

        when(repositoryPort.findById(2L)).thenReturn(Optional.of(r1));

        var res = service.obtenerRol(2L);

        assertThat(res.getId()).isEqualTo(2L);
        assertThat(res.getNombre()).isEqualTo("Rol 2");
        assertThat(res.getDescripcion()).isEqualTo("Desc 2");
    }
}
