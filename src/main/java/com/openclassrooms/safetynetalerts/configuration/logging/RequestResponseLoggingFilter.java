package com.openclassrooms.safetynetalerts.configuration.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        logger.info("Logging Request  {} : {} {}", request.getMethod(), request.getRequestURI(),
                Objects.nonNull(request.getQueryString()) ? request.getQueryString() : "");

        filterChain.doFilter(servletRequest, servletResponse);

        logger.info("Logging Response status code: {}", response.getStatus());
    }
}
