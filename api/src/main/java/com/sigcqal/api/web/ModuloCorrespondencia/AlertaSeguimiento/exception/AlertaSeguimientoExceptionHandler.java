package com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.ErrorEnvioNotificacionException;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.FolioSinAreaAsignadaException;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.FolioYaConcluidoException;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.MensajeAlertaInvalidoException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AlertaSeguimientoExceptionHandler {
    @ExceptionHandler(FolioSinAreaAsignadaException.class)
    public ResponseEntity<Map<String, Object>> handleFolioSinArea(FolioSinAreaAsignadaException ex) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getCodigo(), ex.getMessage());
    }

    @ExceptionHandler(FolioYaConcluidoException.class)
    public ResponseEntity<Map<String, Object>> handleFolioConcluido(FolioYaConcluidoException ex) {
        return build(HttpStatus.CONFLICT, ex.getCodigo(), ex.getMessage());
    }

    @ExceptionHandler(ErrorEnvioNotificacionException.class)
    public ResponseEntity<Map<String, Object>> handleNotificacion(ErrorEnvioNotificacionException ex) {
        return build(HttpStatus.SERVICE_UNAVAILABLE, ex.getCodigo(), ex.getMessage());
    }

    @ExceptionHandler(MensajeAlertaInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleMensajeInvalido(MensajeAlertaInvalidoException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getCodigo(), ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "NF-01", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return build(HttpStatus.BAD_REQUEST, "REQ-01", "Parámetro inválido");
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Map<String, Object>> handleMissingHeader(MissingRequestHeaderException ex) {
        return build(HttpStatus.BAD_REQUEST, "REQ-02", "Falta el encabezado requerido: " + ex.getHeaderName());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, "REQ-03", "Método HTTP no soportado");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandler(NoHandlerFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "NF-00", "Ruta no encontrada");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnhandled(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "ERR-00", "Ocurrió un error inesperado");
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String codigo, String mensaje) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("codigo", codigo);
        body.put("mensaje", mensaje);
        body.put("timestamp", Instant.now().toString());
        return ResponseEntity.status(status).body(body);
    }
}

