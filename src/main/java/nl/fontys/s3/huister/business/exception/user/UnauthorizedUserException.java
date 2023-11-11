package nl.fontys.s3.huister.business.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedUserException extends ResponseStatusException {
    public UnauthorizedUserException() {
        super(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED_USER");
    }
}
