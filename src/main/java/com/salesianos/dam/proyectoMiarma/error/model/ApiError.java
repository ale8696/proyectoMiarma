package com.salesianos.dam.proyectoMiarma.error.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {

    private HttpStatus status;
    private int cod;
    private String message;
    private String route;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiSubError> subErrors;

    public ApiError(HttpStatus estado, String mensaje, String ruta) {
        this.status = estado;
        this.cod = estado.value();
        this.message = mensaje;
        this.route = ruta;
        this.date = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message, String route, List<ApiSubError> subErrors) {
        this(status, message, route);
        this.subErrors = subErrors;
    }
}