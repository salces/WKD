package pl.edu.wat.bookstore.book.web.DTO;

import lombok.*;
import pl.edu.wat.bookstore.book.domain.Book;
import pl.edu.wat.bookstore.book.domain.BookLoan;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputLoanBookDTO {
    private Book book;
    private boolean isActive;
    private boolean isPaid;
    private Date startDate;
    private Date endDate;
}
