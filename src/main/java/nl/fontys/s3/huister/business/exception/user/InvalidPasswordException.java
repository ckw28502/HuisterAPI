package nl.fontys.s3.huister.business.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPasswordException extends ResponseStatusException {
    public InvalidPasswordException() {
        super(HttpStatus.BAD_REQUEST,"INVALID_PASSWORD");
    }
}
