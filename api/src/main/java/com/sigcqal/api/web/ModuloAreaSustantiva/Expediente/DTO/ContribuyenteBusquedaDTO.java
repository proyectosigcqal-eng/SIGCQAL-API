package com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO;
 
import java.time.LocalDateTime;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Resultado de la búsqueda de Persona/Contribuyente por nombre o RFC.
 * Si idContribuyente viene null, la persona existe pero aún no tiene
 * registro en contribuyentes (se creará al guardar el expediente).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContribuyenteBusquedaDTO {
 
    private Long idPersona;
    private Long idContribuyente; // null si la persona no tiene contribuyente todavía
 
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String rfc;
    private String curp;
    private String telefono;
    private String telefonoFijo;
    private String correo;
    private String comunidad;
    private String rec;
    private String identificacionOficial;
    private String numeroIdFolio;
    private String tipoIdentificacion;
    private Long idDireccion;
    private Long idTipoPersona;
    private String nombreTipoPersona;
 
    private LocalDateTime fechaRegistroSistema; // solo si ya es contribuyente
    private String observacionesInternas;       // solo si ya es contribuyente
}