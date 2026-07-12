package com.ana.tenisdemesa.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;
import jakarta.servlet.ServletException;

public class HtmxAwareAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public HtmxAwareAuthenticationEntryPoint() {
        super("/login");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String hxRequest = request.getHeader("HX-Request");
        if ("true".equals(hxRequest)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            super.commence(request, response, authException);
        }
    }
}
