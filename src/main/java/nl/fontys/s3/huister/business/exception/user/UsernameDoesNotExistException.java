package nl.fontys.s3.huister.business.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameDoesNotExistException extends ResponseStatusException {
    public UsernameDoesNotExistException() {
        super(HttpStatus.BAD_REQUEST,"USERNAME_DOES_NOT_EXIST");
    }
}
