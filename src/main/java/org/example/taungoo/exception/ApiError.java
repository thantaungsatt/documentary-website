package org.example.taungoo.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ApiError {
    private int errorCode;
    private String errorMessage;
    private LocalDateTime localDateTime;

    public ApiError(
            int errorCode,
            String errorMessage,
            LocalDateTime localDateTime) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.localDateTime = localDateTime;
    }
}
