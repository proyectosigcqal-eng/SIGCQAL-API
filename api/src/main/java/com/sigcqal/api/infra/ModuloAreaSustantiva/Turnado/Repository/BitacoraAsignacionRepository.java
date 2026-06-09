package com.sigcqal.api.infra.ModuloAreaSustantiva.Turnado.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
import java.time.LocalDateTime;

public interface BitacoraAsignacionRepository
        extends JpaRepository<AsesorEntity, Long> {

    @Modifying
    @Query(value = """
        INSERT INTO sustantiva.bitacora_asignacion
            (id_expediente, id_asesor_anterior, id_asesor_nuevo,
             id_usuario_asigno, fecha_asignacion, ip_origen, motivo)
        VALUES
            (:idExpediente, :idAsesorAnterior, :idAsesorNuevo,
             :idUsuario, :fechaAsignacion, :ip, :motivo)
        """, nativeQuery = true)
    void insertarBitacora(
            @Param("idExpediente")    Integer idExpediente,
            @Param("idAsesorAnterior") Long   idAsesorAnterior,
            @Param("idAsesorNuevo")   Long    idAsesorNuevo,
            @Param("idUsuario")       Integer idUsuario,
            @Param("fechaAsignacion") LocalDateTime fechaAsignacion,
            @Param("ip")              String  ip,
            @Param("motivo")          String  motivo);
}