package nl.fontys.s3.huister.business.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRoleException extends ResponseStatusException {
    public InvalidRoleException() {
        super(HttpStatus.BAD_REQUEST,"INVALID_ROLE");
    }
}
