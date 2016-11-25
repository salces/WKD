package pl.edu.wat.bookstore.book.service.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.wat.bookstore.book.web.DTO.LoanBookDTO;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(code = CONFLICT, reason = "Book already loaned by this user")
public class BookAlreadyLoanedException extends RuntimeException {
    public BookAlreadyLoanedException(String ISBN, String login) {
        super("Book with ISBN " + ISBN +
               " is already loaned by user " + login);
    }
}
