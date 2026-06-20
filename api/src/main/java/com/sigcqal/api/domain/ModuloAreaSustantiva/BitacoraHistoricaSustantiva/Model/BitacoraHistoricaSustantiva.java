package com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Model;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BitacoraHistoricaSustantiva {
    private String tipoEvento;        // Ej: "Registro de Queja", "Emisión de ARI", "Cierre"
    private LocalDateTime fecha;      // Fecha normalizada del movimiento
    private String autorCompleto;     // Nombre del asesor/responsable
    private String descripcion;       // Detalles específicos (ej: "Oficio #123", "Justificación...")
    private String estatus;           // El estatus en el que queda la queja tras este evento
    private String fuente;            // Identificador de origen: "QUEJAS", "CIR", "ACCI", etc.
}