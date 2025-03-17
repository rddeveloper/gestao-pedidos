package br.com.foursales.ordermanagement.exception.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class ForbiddenHandler extends AuthorizationAuthenticationHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {

        if (!response.isCommitted()) {

            status = HttpStatus.FORBIDDEN.value();
            error = "Forbidden";
            messageError = "Access Denied";

            responseClient(request, response, status, error, messageError);


        }

    }
}
