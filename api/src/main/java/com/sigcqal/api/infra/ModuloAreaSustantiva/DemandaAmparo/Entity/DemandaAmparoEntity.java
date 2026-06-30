package com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Entity;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "irl_demanda_amparo", schema = "sustantiva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandaAmparoEntity {

    @Id
    @Column(name = "id_demanda_amparo")
    private Integer idDemandaAmparo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediente")
    private ExpedienteEntity expediente;
}
