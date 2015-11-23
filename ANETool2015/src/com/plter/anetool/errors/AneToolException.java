package com.plter.anetool.errors;

/**
 * Created by plter on 11/23/15.
 */
public class AneToolException extends RuntimeException {

    public AneToolException() {
    }

    public AneToolException(String message) {
        super(message);
    }

    public AneToolException(String message, Throwable cause) {
        super(message, cause);
    }

    public AneToolException(Throwable cause) {
        super(cause);
    }
}
