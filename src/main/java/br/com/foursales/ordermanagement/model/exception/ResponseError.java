package br.com.foursales.ordermanagement.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseError {

    private String message;

    private List<ObjectInvalidResponse> invalidFields;

    private String status;

    private int code;

    public ResponseError(String messageError, String httpStatus, int code) {
        this.message = messageError;
        this.status = httpStatus;
        this.code = code;
    }
}