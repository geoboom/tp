package seedu.address.logic.phase.exceptions;

public class PhaseInvalidException extends Exception {
    public PhaseInvalidException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code PhaseInvalidException} with the specified detail {@code message} and {@code cause}.
     */
    public PhaseInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

}
