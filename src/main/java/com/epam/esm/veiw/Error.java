package com.epam.esm.veiw;

/**
 * Error is the data class, which used for shoving error.
 */
public class Error {
    private final int code;
    private final String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
