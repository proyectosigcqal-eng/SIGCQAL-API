package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Entity.AcuseReciboInternoEntity;

public interface AcuseReciboInternoJpaRepository
    extends JpaRepository<AcuseReciboInternoEntity, Long> {

    @Query("""
        SELECT a FROM AcuseReciboInternoEntity a
        LEFT JOIN FETCH a.memorandum m
        LEFT JOIN FETCH m.correspondencia
        LEFT JOIN FETCH m.usuarioEmisor
        LEFT JOIN FETCH m.usuarioFirmante
        LEFT JOIN FETCH m.area
        LEFT JOIN FETCH a.usuarioRevisor
        """)
    List<AcuseReciboInternoEntity> findAllConRelaciones();

    @Query("""
        SELECT a FROM AcuseReciboInternoEntity a
        LEFT JOIN FETCH a.memorandum m
        LEFT JOIN FETCH m.correspondencia
        LEFT JOIN FETCH m.usuarioEmisor
        LEFT JOIN FETCH m.usuarioFirmante
        LEFT JOIN FETCH m.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE a.idAcuse = :id
        """)
    Optional<AcuseReciboInternoEntity> findByIdConRelaciones(@Param("id") Long id);

    @Query("""
        SELECT a FROM AcuseReciboInternoEntity a
        LEFT JOIN FETCH a.memorandum m
        LEFT JOIN FETCH m.correspondencia
        LEFT JOIN FETCH m.usuarioEmisor
        LEFT JOIN FETCH m.usuarioFirmante
        LEFT JOIN FETCH m.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE a.usuarioRevisor.id = :idUsuario
        """)
    List<AcuseReciboInternoEntity> findByUsuarioRevisor_IdConRelaciones(@Param("idUsuario") Long idUsuario);

    @Query("""
        SELECT a FROM AcuseReciboInternoEntity a
        LEFT JOIN FETCH a.memorandum m
        LEFT JOIN FETCH m.correspondencia
        LEFT JOIN FETCH m.usuarioEmisor
        LEFT JOIN FETCH m.usuarioFirmante
        LEFT JOIN FETCH m.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE a.esDelArea = true AND m.area.id = :idArea
        """)
    List<AcuseReciboInternoEntity> findByEsDelAreaTrueAndMemorandum_Area_IdConRelaciones(
            @Param("idArea") Long idArea);

    @Query("""
        SELECT a FROM AcuseReciboInternoEntity a
        LEFT JOIN FETCH a.memorandum m
        LEFT JOIN FETCH m.correspondencia
        LEFT JOIN FETCH m.usuarioEmisor
        LEFT JOIN FETCH m.usuarioFirmante
        LEFT JOIN FETCH m.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE m.id = :idMemorandum
        """)
    List<AcuseReciboInternoEntity> findByMemorandum_IdConRelaciones(@Param("idMemorandum") Long idMemorandum);

    List<AcuseReciboInternoEntity> findByUsuarioRevisor_Id(Long idUsuario);

    boolean existsByMemorandum_Id(Long idMemorandum);

    List<AcuseReciboInternoEntity> findByEsDelAreaTrueAndMemorandum_Area_Id(Long id);

    List<AcuseReciboInternoEntity> findByEsDelAreaFalse();

    @Query("""
        SELECT a FROM AcuseReciboInternoEntity a
        LEFT JOIN FETCH a.memorandum m
        LEFT JOIN FETCH m.correspondencia
        LEFT JOIN FETCH m.usuarioEmisor
        LEFT JOIN FETCH m.usuarioFirmante
        LEFT JOIN FETCH m.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE a.esDelArea = false
        """)
    List<AcuseReciboInternoEntity> findByEsDelAreaFalseConRelaciones();

    List<AcuseReciboInternoEntity> findByMemorandum_Id(Long idMemorandum);
}
