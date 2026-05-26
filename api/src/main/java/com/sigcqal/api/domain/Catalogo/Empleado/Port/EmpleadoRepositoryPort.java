package com.sigcqal.api.domain.Catalogo.Empleado.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Empleado.Model.Empleado;

public interface EmpleadoRepositoryPort {
    Optional<Empleado> findById(Long id);
    List<Empleado> findAll();
}
