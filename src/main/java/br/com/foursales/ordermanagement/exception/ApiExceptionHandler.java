package br.com.foursales.ordermanagement.exception;


import br.com.foursales.ordermanagement.exception.auth.TokenRefreshException;
import br.com.foursales.ordermanagement.model.exception.ObjectInvalidResponse;
import br.com.foursales.ordermanagement.model.exception.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler<T> extends ResponseEntityExceptionHandler {

    private static final HttpStatus HTTP_STATUS_BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final HttpStatus HTTP_STATUS_NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final HttpStatus HTTP_STATUS_CONFLICT = HttpStatus.CONFLICT;
    private static final HttpStatus HTTP_STATUS_TOO_MANY_REQUESTS = HttpStatus.TOO_MANY_REQUESTS;
    private static final HttpStatus HTTP_STATUS_INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    private static final HttpStatus HTTP_STATUS_FORBIDDEN = HttpStatus.FORBIDDEN;

    private final MessageSource messageSource;

    @Autowired
    ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Response<T>> handleBusinessException(BusinessException businessException) {
        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(businessException.getMessage(), HTTP_STATUS_BAD_REQUEST);
        return buildResponseEntity(response, HTTP_STATUS_BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Response<T>> handleNotFoundException(NotFoundException notFoundException) {
        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(notFoundException.getMessage(), HTTP_STATUS_NOT_FOUND);
        return buildResponseEntity(response, HTTP_STATUS_NOT_FOUND);
    }

    @ExceptionHandler(value = { HttpClientErrorException.Conflict.class })
    protected ResponseEntity<Response<T>> handleConflictException(HttpClientErrorException exception) {
        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(exception.getLocalizedMessage(), HTTP_STATUS_CONFLICT);
        return buildResponseEntity(response, HTTP_STATUS_CONFLICT);
    }

    @ExceptionHandler(value = { HttpClientErrorException.TooManyRequests.class })
    protected ResponseEntity<Response<T>> handleTooManyRequestException(HttpClientErrorException exception) {
        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(exception.getLocalizedMessage(), HTTP_STATUS_TOO_MANY_REQUESTS);
        return buildResponseEntity(response, HTTP_STATUS_TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(value = { ServerErrorException.class })
    protected ResponseEntity<Response<T>> handleInternalServerErrorException(ServerErrorException exception) {
        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(exception.getLocalizedMessage(), HTTP_STATUS_INTERNAL_SERVER_ERROR);
        return buildResponseEntity(response, HTTP_STATUS_INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<Response<T>> handleUsernameNotFoundException(BadCredentialsException badCredentialsException) {
        Response<T> response = new Response<>();
        response.addErrorMsgToResponse("dados login inválido", HTTP_STATUS_BAD_REQUEST);
        return buildResponseEntity(response, HTTP_STATUS_BAD_REQUEST);
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    public ResponseEntity<Response<T>> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {

        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(ex.getMessage(), HTTP_STATUS_FORBIDDEN);
        return buildResponseEntity(response, HTTP_STATUS_FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode statusCode,
                                                                  @NonNull WebRequest request) {

        List<ObjectInvalidResponse> objectInvalidResponses = new ArrayList<>();
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            objectInvalidResponses.add(new ObjectInvalidResponse(fieldError.getObjectName(),
                    fieldError.getField(),fieldError.getRejectedValue(),
                    messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())));
        }

        Response<T> response = new Response<>();
        response.addErrorsObjectInvalidToResponse("Erro de validação de campo", objectInvalidResponses, statusCode);
        return new ResponseEntity<>(response, statusCode);

    }

    private ResponseEntity<Response<T>> buildResponseEntity(Response<T> response, HttpStatus httpStatus) {
        return new ResponseEntity<>(response, httpStatus);
    }

}
