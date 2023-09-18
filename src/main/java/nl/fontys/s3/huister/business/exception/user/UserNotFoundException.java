package nl.fontys.s3.huister.business.exception.user;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(HttpStatusCode status) {
        super(status,"USER_NOT_FOUND");
    }
}
