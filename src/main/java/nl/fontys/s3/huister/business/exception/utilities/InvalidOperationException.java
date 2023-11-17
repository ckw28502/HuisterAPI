package nl.fontys.s3.huister.business.exception.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidOperationException extends ResponseStatusException {
    public InvalidOperationException() {
        super(HttpStatus.FORBIDDEN,"INVALID_OPERATION");
    }
}
