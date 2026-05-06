package com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Entity.OficioEntity;

import java.util.List;
@Repository
public interface OficioJpaRepository extends JpaRepository<OficioEntity, Long> {
    Optional<OficioEntity> findByFolioUnico(String folioUnico);
    List<OficioEntity> findByAreaId(Long idArea);
    
    @Query("SELECT o FROM OficioEntity o LEFT JOIN FETCH o.area LEFT JOIN FETCH o.usuarioEmisor LEFT JOIN FETCH o.usuarioFirmante WHERE o.id = :id")
    Optional<OficioEntity> findByIdWithRelations(@Param("id") Long id);

}
