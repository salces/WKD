package pl.edu.wat.bookstore.book.web.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanBookDTO {
    String isbn;
    int days;
}
