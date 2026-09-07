package com.indomarco.technical.utility;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.indomarco.technical.model.WebResponse;

@Component
public class ResponseUtil {
    public <T> ResponseEntity<WebResponse<T>> successResponse(String message, T data) {
        WebResponse<T> webResponse = WebResponse.<T>builder()
                .message(message)
                .data(data)
                .errors(null)
                .build();
        return ResponseEntity.ok(webResponse);
    }

    public <T> ResponseEntity<WebResponse<T>> errorResponse(String message, T error, HttpStatusCode status) {
        WebResponse<T> webResponse = WebResponse.<T>builder()
                .message(message)
                .data(null)
                .errors(error)
                .build();
        return ResponseEntity.status(status).body(webResponse);
    }

}
