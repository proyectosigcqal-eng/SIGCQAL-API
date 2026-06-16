package com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;

@Repository
public interface QuejaJPARepository extends JpaRepository<QuejaEntity, Integer> {

    @Query("SELECT q FROM QuejaEntity q " +
           "LEFT JOIN FETCH q.expediente e " +
           "LEFT JOIN FETCH e.representanteLegal rep " +
           "LEFT JOIN FETCH e.contribuyente c " +
           "LEFT JOIN FETCH c.persona p_cont " + // Asumiendo que Contribuyente tiene la relación 'persona'
           "LEFT JOIN FETCH q.asesor a " +
           "LEFT JOIN FETCH a.persona p_ase " +  // Asumiendo que Asesor tiene la relación 'persona'
           "WHERE q.idQueja = :idQueja")
    Optional<QuejaEntity> findByIdConRelaciones(@Param("idQueja") Integer idQueja);

    @Query("SELECT q FROM QuejaEntity q " +
           "LEFT JOIN FETCH q.expediente e " +
           "LEFT JOIN FETCH e.representanteLegal rep " +
           "LEFT JOIN FETCH e.contribuyente c " +
           "LEFT JOIN FETCH c.persona p_cont " +
           "LEFT JOIN FETCH q.asesor a " +
           "LEFT JOIN FETCH a.persona p_ase")
    List<QuejaEntity> findAllConRelaciones();
}