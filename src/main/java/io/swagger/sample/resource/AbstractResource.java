package io.swagger.sample.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.sample.exception.AlreadyExistedException;
import io.swagger.sample.exception.BadRequestException;
import io.swagger.sample.exception.NotFoundException;
import io.swagger.sample.models.ApiResponse;

public class AbstractResource {

    @CrossOrigin(allowedHeaders = "foo", origins = "*")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse handleNotFoundException(NotFoundException e) {
        return new ApiResponse(ApiResponse.ERROR, e.getMessage());
    }
    
    @CrossOrigin(allowedHeaders = "foo", origins = "*")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiResponse handleBadRequestException(BadRequestException e) {
        return new ApiResponse(ApiResponse.WARNING, e.getMessage());
    }
    
    @CrossOrigin(allowedHeaders = "foo", origins = "*")
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistedException.class)
    public ApiResponse handleAlreadyExistedException(AlreadyExistedException e) {
        return new ApiResponse(ApiResponse.ALREADY_EXISTED, e.getMessage());
    }
}
