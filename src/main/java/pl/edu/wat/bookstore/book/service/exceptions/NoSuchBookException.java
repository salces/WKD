package pl.edu.wat.bookstore.book.service.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(code = NOT_FOUND, reason = "No such book found")
public class NoSuchBookException extends RuntimeException {
    public NoSuchBookException(String ISBN) {
        super("Book with ISBN " + ISBN + " not found!");
    }
}
