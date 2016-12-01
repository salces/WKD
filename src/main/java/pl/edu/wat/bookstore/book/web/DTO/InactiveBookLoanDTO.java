package pl.edu.wat.bookstore.book.web.DTO;

import lombok.*;
import pl.edu.wat.bookstore.book.domain.Book;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InactiveBookLoanDTO {
    private Book book;
    private String user;
    private String bookLoanID;
}
