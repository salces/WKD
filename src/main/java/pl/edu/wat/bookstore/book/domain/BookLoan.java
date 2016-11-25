package pl.edu.wat.bookstore.book.domain;

import lombok.*;
import org.jongo.marshall.jackson.oid.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wat.domain.User;

import java.time.LocalDate;

@Document(collection = "bookLoans")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLoan {

    @Id
    @Generated
    Long ID;

    private String ISBN;
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;

    @DBRef
    private User owner;
}
