package com.sigcqal.api.application.ModuloAreaSustantiva.BandejaRepresentacionLegal;

import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Model.RepresentacionBandeja;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Port.BandejaRepresentacionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BandejaRepresentacionService {

    private static final String ID_TIPO_TRAMITE_REPRESENTACION_LEGAL = "3";

    private final BandejaRepresentacionPort bandejaRepresentacionPort;

    public List<RepresentacionBandeja> obtenerBandeja( String search, String estatus) {
        return bandejaRepresentacionPort.obtenerBandeja(
            search,
            estatus,
            ID_TIPO_TRAMITE_REPRESENTACION_LEGAL
        );
    }
}
