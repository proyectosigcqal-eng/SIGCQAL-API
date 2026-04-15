package com.sigcqal.api.infra.Catalogo.Rol.Adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sigcqal.api.domain.Catalogo.Rol.Model.Rol;
import com.sigcqal.api.infra.Catalogo.Rol.Entity.RolEntity;
import com.sigcqal.api.infra.Catalogo.Rol.Mapper.RolMapper;
import com.sigcqal.api.infra.Catalogo.Rol.Repository.RolJpaRepository;

@ExtendWith(MockitoExtension.class)
class RolRepositoryAdapterTest {
    @Mock
    private RolJpaRepository jpaRepository;

    @Mock
    private RolMapper mapper;

    @InjectMocks
    private RolRepositoryAdapter adapter;

    @Test
    void findById_mapeaOptional() {
        RolEntity entity = new RolEntity();
        entity.setId(1L);
        entity.setNombre("Rol 1");
        entity.setDescripcion("Desc 1");

        Rol domain = new Rol();
        domain.setId(1L);
        domain.setNombre("Rol 1");
        domain.setDescripcion("Desc 1");

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.findById(1L);

        assertThat(res).contains(domain);
    }

    @Test
    void findAll_mapeaLista() {
        RolEntity entity = new RolEntity();
        entity.setId(1L);
        entity.setNombre("Rol 1");

        Rol domain = new Rol();
        domain.setId(1L);
        domain.setNombre("Rol 1");

        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.findAll();

        assertThat(res).containsExactly(domain);
    }
}
