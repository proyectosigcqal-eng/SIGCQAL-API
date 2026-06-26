package com.sigcqal.api.infra.ModuloAreaSustantiva.Turnado.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
import java.util.List;

public interface AsesorTurnadoRepository
        extends JpaRepository<AsesorEntity, Long> {

    // Trae asesores ordenados por carga actual ASC y última asignación ASC
    // → el que tiene menos carga y fue asignado hace más tiempo va primero (Round Robin)
    @Query(value = """
    SELECT a.id_asesores,
           CONCAT(p.nombre, ' ', p.apellido_paterno) AS nombre_completo,
           a.carga_actual,
           a.ultima_asignacion_at
    FROM sustantiva.asesores a
    JOIN catalogos.personas p ON p.id_persona = a.id_persona
    WHERE a.activo = true                              
    ORDER BY a.carga_actual ASC, a.ultima_asignacion_at ASC NULLS FIRST
    """, nativeQuery = true)
List<Object[]> findAsesoresOrdenadosRaw();

    // Incrementa carga y actualiza timestamp de última asignación
    @Modifying
    @Query(value = """
        UPDATE sustantiva.asesores
        SET carga_actual         = COALESCE(carga_actual, 0) + 1,
            ultima_asignacion_at = NOW()
        WHERE id_asesores = :idAsesor
        """, nativeQuery = true)
    void incrementarCarga(@Param("idAsesor") Long idAsesor);
}