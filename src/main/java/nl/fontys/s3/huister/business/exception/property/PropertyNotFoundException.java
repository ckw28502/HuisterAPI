package nl.fontys.s3.huister.business.exception.property;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PropertyNotFoundException extends ResponseStatusException {
    public PropertyNotFoundException() {
        super(HttpStatus.BAD_REQUEST,"PROPERTY_NOT_FOUND");
    }
}
