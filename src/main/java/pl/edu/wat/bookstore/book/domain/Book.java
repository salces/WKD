package pl.edu.wat.bookstore.book.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Document(collection = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Serializable{

    @Id
    String ISBN;

    @NotNull
    String tittle;

    @NotNull
    String author;

    @NotNull
    int publicationYear;

    @NotNull
    String publisher;

    @NotNull
    String smallImageUrl;

    @NotNull
    String mediumImageUrl;

    @NotNull
    String largeImageUrl;


}
