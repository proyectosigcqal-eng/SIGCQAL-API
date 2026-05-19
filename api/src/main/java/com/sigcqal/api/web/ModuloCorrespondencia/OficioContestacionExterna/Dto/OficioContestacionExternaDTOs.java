package com.sigcqal.api.web.ModuloCorrespondencia.OficioContestacionExterna.Dto;

import lombok.Data;

public class OficioContestacionExternaDTOs {

    @Data
    public static class Request {
        private Long idCorrespondencia;
        private Long idUsuarioEmisor;
        private String numOficioSalida;
        private String asuntoContestacion;
        private String cuerpoOficioTexto;
        private String urlPdfFinal;
    }

    @Data
    public static class Response {
        private Long idOficioContestacion;
        private Long idCorrespondencia;
        private Long idUsuarioEmisor;
        private String numOficioSalida;
        private String asuntoContestacion;
        private String cuerpoOficioTexto;
        private String urlPdfFinal;
        private String fechaEmision;
        private String folioCorrespondencia;
        private String asuntoCorrespondencia;
    }
}
