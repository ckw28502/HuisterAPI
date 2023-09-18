package nl.fontys.s3.huister.business.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameExistException extends ResponseStatusException {
    public UsernameExistException() {
        super(HttpStatus.BAD_REQUEST,"USERNAME_EXISTS");
    }
}
