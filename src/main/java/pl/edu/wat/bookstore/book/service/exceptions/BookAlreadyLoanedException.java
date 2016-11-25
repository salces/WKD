package pl.edu.wat.bookstore.book.service.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.wat.bookstore.book.web.DTO.LoanBookDTO;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(code = CONFLICT, reason = "Book already loaned by this user")
public class BookAlreadyLoanedException extends RuntimeException {
    public BookAlreadyLoanedException(LoanBookDTO loanBookDTO) {
        super("Book with ISBN " + loanBookDTO.getISBN() +
               " is already loaned by user " + loanBookDTO.getUserID());
    }
}
