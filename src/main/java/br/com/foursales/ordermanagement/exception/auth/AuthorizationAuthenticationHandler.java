package br.com.foursales.ordermanagement.exception.auth;

import br.com.foursales.ordermanagement.model.exception.StandardError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
public abstract class AuthorizationAuthenticationHandler {

    protected int status;

    protected String error;

    protected String messageError;

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void responseClient(HttpServletRequest request, HttpServletResponse response, int status, String error, String messageError) {
        try {

            objectMapper.registerModule(new JavaTimeModule());

            response.setStatus(status);
            response.setContentType("application/json");

            String uri = request.getRequestURI();

            StandardError err = new StandardError(status, error, messageError, uri);

            response.getWriter().write(objectMapper.writeValueAsString(err));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
