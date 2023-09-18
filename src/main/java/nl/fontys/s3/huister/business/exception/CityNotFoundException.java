package nl.fontys.s3.huister.business.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class CityNotFoundException extends ResponseStatusException {
    public CityNotFoundException(HttpStatusCode status) {
        super(status,"CITY_NOT_FOUND");
    }
}
