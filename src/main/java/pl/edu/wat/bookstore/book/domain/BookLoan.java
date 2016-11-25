package pl.edu.wat.bookstore.book.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wat.domain.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Document(collection = "bookLoans")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLoan implements Serializable{

    @Id
    private String ID;

    private String ISBN;
    private int days;
    private Date startDate;
    private Date endDate;
    private boolean isActive;

    @DBRef
    private User owner;
}
