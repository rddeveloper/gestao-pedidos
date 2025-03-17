package br.com.foursales.ordermanagement.model.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private T data;

    private Object error;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    public void addDataResponse(T data) {
        setData(data);
    }

    public void addErrorMsgToResponse(String messageError, HttpStatus httpStatus) {
        setError(new ResponseError(messageError, httpStatus.getReasonPhrase(), httpStatus.value()));
    }

    public void addErrorsObjectInvalidToResponse(String messageError, List<ObjectInvalidResponse> objectInvalidResponses, HttpStatusCode statusCode) {
        setError(new ResponseError(messageError, objectInvalidResponses, HttpStatus.valueOf(statusCode.value()).getReasonPhrase(), statusCode.value()));
    }
}
