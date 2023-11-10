package nl.fontys.s3.huister.business.exception.city;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CityNotFoundException extends ResponseStatusException {
    public CityNotFoundException() {
        super(HttpStatus.BAD_REQUEST,"CITY_NOT_FOUND");
    }
}
