package com.sigcqal.api.infra.Catalogo.Personal.Entity;

import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "personal", schema = "catalogos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personal")
    private Long idPersonal;

    // Relación ManyToOne: Muchas personas pueden estar en la tabla personal
    // (o OneToOne si la regla es estrictamente una persona por cada registro de personal)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false)
    private PersonaEntity persona;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "activo")
    private Boolean activo;

    // Método para asegurar el timestamp antes de persistir
    @PrePersist
    protected void onCreate() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
        if (this.activo == null) {
            this.activo = true;
        }
    }
}
