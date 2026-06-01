package com.sigcqal.api.infra.Expediente.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Expediente.Entity.ContribuyenteEntity;

public interface ContribuyenteJPARepository extends JpaRepository<ContribuyenteEntity, Long> {
    Optional<ContribuyenteEntity> findByRfc(String rfc);
}
