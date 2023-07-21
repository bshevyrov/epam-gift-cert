package com.epam.esm.veiw;

import lombok.Value;

/**
 * Error is the data class, which used for shoving error.
 */
@Value
public class ErrorResponse {
    int code;
    String message;
}
