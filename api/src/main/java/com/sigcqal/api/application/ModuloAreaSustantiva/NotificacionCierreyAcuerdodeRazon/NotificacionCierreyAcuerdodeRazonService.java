package com.sigcqal.api.application.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Model.NotificacionCierreyAcuerdodeRazon;
import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Port.NotificacionCierreyAcuerdodeRazonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificacionCierreyAcuerdodeRazonService {

    private final NotificacionCierreyAcuerdodeRazonRepositoryPort cierreRepository;

    
    @Transactional
    public NotificacionCierreyAcuerdodeRazon registrarCierreDefinitivo(NotificacionCierreyAcuerdodeRazon cierre) {
        
        // 1. Validar si ya existe un cierre previo para este expediente
        List<NotificacionCierreyAcuerdodeRazon> cierresPrevios = cierreRepository.findById(cierre.getIdExpediente());
        if (!cierresPrevios.isEmpty()) {
            throw new RuntimeException("El expediente con ID " + cierre.getIdExpediente() + " ya cuenta con un cierre definitivo registrado.");
        }

        // 2. Asegurar que la fecha de cierre esté presente
        if (cierre.getFechaCierre() == null) {
            cierre.setFechaCierre(LocalDateTime.now());
        }
        // 4. Registrar el cierre en la base de datos y retornar el objeto persistido
        return cierreRepository.save(cierre);
    }    
}
