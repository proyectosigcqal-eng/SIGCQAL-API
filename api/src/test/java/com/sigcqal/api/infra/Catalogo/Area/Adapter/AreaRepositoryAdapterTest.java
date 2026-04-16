package com.sigcqal.api.infra.Catalogo.Area.Adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sigcqal.api.domain.Catalogo.Area.Model.Area;
import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;
import com.sigcqal.api.infra.Catalogo.Area.Mapper.AreaMapper;
import com.sigcqal.api.infra.Catalogo.Area.Repository.AreaJpaRepository;

@ExtendWith(MockitoExtension.class)
class AreaRepositoryAdapterTest {
    @Mock
    private AreaJpaRepository jpaRepository;

    @Mock
    private AreaMapper mapper;

    @InjectMocks
    private AreaRepositoryAdapter adapter;

    @Test
    void findById_mapeaOptional() {
        AreaEntity entity = new AreaEntity();
        entity.setId(1L);
        entity.setNombre("Area 1");
        entity.setDescripcion("Desc 1");

        Area domain = new Area();
        domain.setId(1L);
        domain.setNombre("Area 1");
        domain.setDescripcion("Desc 1");

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.findById(1L);

        assertThat(res).contains(domain);
    }

    @Test
    void findAll_mapeaLista() {
        AreaEntity entity = new AreaEntity();
        entity.setId(1L);
        entity.setNombre("Area 1");

        Area domain = new Area();
        domain.setId(1L);
        domain.setNombre("Area 1");

        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.findAll();

        assertThat(res).containsExactly(domain);
    }
}
