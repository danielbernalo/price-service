package com.commerce.prices.adapter.web;

import com.commerce.prices.domain.exception.PriceNotFoundException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                          WebProperties.Resources resources,
                                          ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(
                RequestPredicates.all(), request -> renderErrorResponse(request, errorAttributes));
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request, ErrorAttributes errorAttributes) {
        Throwable error = errorAttributes.getError(request);

        if (error instanceof PriceNotFoundException) {
            return createErrorResponse(HttpStatus.NOT_FOUND, error.getMessage());
        }

        if (error instanceof ServerWebInputException) {
            String message = "Invalid parameter format: " + error.getMessage();
            return createErrorResponse(HttpStatus.BAD_REQUEST, message);
        }

        if (error instanceof final ResponseStatusException responseError) {
            return createErrorResponse(HttpStatus.valueOf(responseError.getStatusCode().value()),
                    responseError.getReason());
        }

        // Default error response
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    private Mono<ServerResponse> createErrorResponse(HttpStatus status, String message) {
        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new ErrorResponse(
                        status.value(),
                        message
                )));
    }
}


record ErrorResponse(int status, String message) {
}