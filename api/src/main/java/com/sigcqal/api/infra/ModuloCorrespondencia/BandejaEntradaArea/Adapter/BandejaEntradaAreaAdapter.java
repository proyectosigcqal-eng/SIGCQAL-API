package com.sigcqal.api.infra.ModuloCorrespondencia.BandejaEntradaArea.Adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.BandejaEntradaArea.Model.BandejaEntradaArea;
import com.sigcqal.api.domain.ModuloCorrespondencia.BandejaEntradaArea.Port.IBandejaEntradaAreaPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.BandejaEntradaArea.Mapper.BandejaEntradaAreaMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.BandejaEntradaArea.Repository.BandejaEntradaAreaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BandejaEntradaAreaAdapter implements IBandejaEntradaAreaPort {
    private final BandejaEntradaAreaJpaRepository repository;
    private final BandejaEntradaAreaMapper mapper;

    @Override
    public BandejaEntradaArea guardar(BandejaEntradaArea bandeja) {
        var entity = mapper.toEntity(bandeja);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<BandejaEntradaArea> listarPorArea(Long idArea) {
        return repository.findByArea_Id(idArea).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<BandejaEntradaArea> listarTodas() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<BandejaEntradaArea> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}

