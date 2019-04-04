package io.project.app.exceptions;

/**
 *
 * @author Admin
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6936410732203003964L;

    public NotFoundException(String message) {
        super(message);
    }

}
