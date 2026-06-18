package com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.Queja;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Port.QuejaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Mapper.QuejaMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuejaAdapter implements QuejaRepositoryPort {

    private final QuejaJPARepository repository;
    private final QuejaMapper        mapper;

    @Override
    public Optional<Queja> findById(Integer idQueja) {
        return repository.findByIdConRelaciones(idQueja).map(mapper::toDomain);
    }

    @Override
    public List<Queja> findAll() {
        return repository.findAllConRelaciones().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void admitir(Integer idDetalleAsesoria, Integer idExpediente) {
        repository.admitirQueja(idDetalleAsesoria, idExpediente);
    }

    @Override
    @Transactional
    public void requerirAclaracion(Integer idExpediente) {
        repository.requerirAclaracion(idExpediente);
    }

    @Override
@Transactional
public void actualizarRequisitos(String folio, Boolean identificacion,
                                  Boolean actosFiscales, Boolean narrativa,
                                  Boolean competencia) {
    repository.actualizarRequisitos(folio, identificacion,
                                    actosFiscales, narrativa, competencia);
}

}