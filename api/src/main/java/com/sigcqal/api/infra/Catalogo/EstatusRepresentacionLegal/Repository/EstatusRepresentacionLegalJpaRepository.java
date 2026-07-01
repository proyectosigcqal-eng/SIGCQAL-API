package com.sigcqal.api.infra.Catalogo.EstatusRepresentacionLegal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.Catalogo.EstatusRepresentacionLegal.Entity.EstatusRepresentacionLegalEntity;

@Repository
public interface EstatusRepresentacionLegalJpaRepository extends JpaRepository<EstatusRepresentacionLegalEntity, Long> {

    List<EstatusRepresentacionLegalEntity> findAllByOrderByIdAsc();
}
