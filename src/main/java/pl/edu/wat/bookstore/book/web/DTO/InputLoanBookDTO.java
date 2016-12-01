package pl.edu.wat.bookstore.book.web.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputLoanBookDTO {
    String isbn;
    int days;
}
