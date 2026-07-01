package com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Repository;



import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DatosQuejoso {
    private final String nombreQuejoso;
    private final String calleQuejoso;
    private final String numCalleQuejoso;  // ← nuevo: num_ext
    private final String coloniaQuejoso;
    private final String cpQuejoso;         // ← nuevo: cp

    public static DatosQuejoso from(Object[] row) {
        if (row == null || row.length == 0) {
            return new DatosQuejoso("", "", "", "", "");
        }
        return new DatosQuejoso(
            (row.length > 0 && row[0] != null) ? row[0].toString() : "",
            (row.length > 1 && row[1] != null) ? row[1].toString() : "",
            (row.length > 2 && row[2] != null) ? row[2].toString() : "",
            (row.length > 3 && row[3] != null) ? row[3].toString() : "",
            (row.length > 4 && row[4] != null) ? row[4].toString() : ""
        );
    }
}