package org.mickael.libraryclientweb.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoquer, Response response) {
        if (response.status() == 400){
            return new BookBadRequestException("Requête incorrecte");
        } else if (response.status() == 404){
            return new BookNotFoundException("Book not found");
        }
        return defaultErrorDecoder.decode(invoquer, response);
    }
}
