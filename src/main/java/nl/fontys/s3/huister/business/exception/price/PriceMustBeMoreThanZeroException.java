package nl.fontys.s3.huister.business.exception.price;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PriceMustBeMoreThanZeroException extends ResponseStatusException {
    public PriceMustBeMoreThanZeroException() {
        super(HttpStatus.BAD_REQUEST,"PRICE_HAS_TO_BE_MORE_THAN_ZERO");
    }
}
