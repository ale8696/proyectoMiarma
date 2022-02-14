package com.salesianos.dam.proyectoMiarma.error.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiValidationSubError extends ApiSubError {

    private String object;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String form;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object failValue;


}
