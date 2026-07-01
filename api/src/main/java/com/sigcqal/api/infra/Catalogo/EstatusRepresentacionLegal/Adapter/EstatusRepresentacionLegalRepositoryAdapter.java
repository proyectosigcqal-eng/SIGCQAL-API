package com.sigcqal.api.infra.Catalogo.EstatusRepresentacionLegal.Adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.EstatusRepresentacionLegal.Model.EstatusRepresentacionLegal;
import com.sigcqal.api.domain.Catalogo.EstatusRepresentacionLegal.Port.EstatusRepresentacionLegalRepositoryPort;
import com.sigcqal.api.infra.Catalogo.EstatusRepresentacionLegal.Mapper.EstatusRepresentacionLegalMapper;
import com.sigcqal.api.infra.Catalogo.EstatusRepresentacionLegal.Repository.EstatusRepresentacionLegalJpaRepository;

@Component
public class EstatusRepresentacionLegalRepositoryAdapter implements EstatusRepresentacionLegalRepositoryPort {

    private final EstatusRepresentacionLegalJpaRepository jpaRepository;
    private final EstatusRepresentacionLegalMapper mapper;

    public EstatusRepresentacionLegalRepositoryAdapter(EstatusRepresentacionLegalJpaRepository jpaRepository,
            EstatusRepresentacionLegalMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<EstatusRepresentacionLegal> findAll() {
        return mapper.toDomainList(jpaRepository.findAllByOrderByIdAsc());
    }
}
