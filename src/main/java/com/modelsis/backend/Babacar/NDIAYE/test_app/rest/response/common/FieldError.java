package com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class FieldError {
    private String field;
    private String errorCode;
}
