package main.exception;

public class WorkNotFoundException extends RuntimeException {
    public WorkNotFoundException(String message) {
        super(message);
    }
}
