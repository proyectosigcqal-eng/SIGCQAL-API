package com.sigcqal.api.domain.ModuloCorrespondencia.BandejaEntradaArea.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloCorrespondencia.BandejaEntradaArea.Model.BandejaEntradaArea;

public interface IBandejaEntradaAreaPort {
    BandejaEntradaArea guardar(BandejaEntradaArea bandeja);

    List<BandejaEntradaArea> listarPorArea(Long idArea);

    List<BandejaEntradaArea> listarTodas();

    Optional<BandejaEntradaArea> buscarPorId(Long id);
}

