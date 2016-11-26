package pl.edu.wat.bookstore.book.service.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(code = NOT_FOUND)
public class NoSuchLoanedBookException extends RuntimeException {
}
