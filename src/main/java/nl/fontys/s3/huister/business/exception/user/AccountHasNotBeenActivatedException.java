package nl.fontys.s3.huister.business.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountHasNotBeenActivatedException extends ResponseStatusException {
    public AccountHasNotBeenActivatedException() {
        super(HttpStatus.BAD_REQUEST,"ACCOUNT_HAS_NOT_BEEN_ACTIVATED");
    }
}
