package com.sigcqal.api.web.Admin.Dto;

import lombok.Data;
import java.util.List;

@Data
public class ActualizarRolesRequestDTO {
    private List<Long> idRoles;
}