package com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ErrorResponse {
    private Integer httpStatus;
    private String exception;
    private String message;
    private List<FieldError> fieldErrors;
}
