package com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class Response<T> {
    private HttpStatus status;
    private T payload;
    private int size;
    private Object metadata;
    private Object message;

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.OK);
        return response;
    }

    public static <T> Response<T> notFound() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.NOT_FOUND);
        return response;
    }
    public static <T> Response<T> badRequest() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.BAD_REQUEST);
        return response;
    }
}
