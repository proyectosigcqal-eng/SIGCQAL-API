package com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
}
