package com.beyond.ticketLink.common.advice;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.common.view.ApiErrorView;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@RestControllerAdvice
public class TicketLinkControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(TicketLinkException.class)
    public ResponseEntity<?> clientAbortException() {
        return new ResponseEntity<>(new ApiErrorView(Collections.singletonList(MessageType.INTERNAL_SERVER_ERROR)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TicketLinkException.class)
    public ResponseEntity<?> onMessageException(TicketLinkException ex) {
        return new ResponseEntity<>(new ApiErrorView(ex), ex.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorView(MessageType.BAD_REQUEST, ex.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorView(MessageType.BAD_REQUEST, ex.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorView(MessageType.BAD_REQUEST, ex.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorView(MessageType.BAD_REQUEST, ex.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorView(MessageType.BAD_REQUEST, ex.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorView(MessageType.INTERNAL_SERVER_ERROR, ex.getMessage()), statusCode);
    }
}
