package pl.edu.wat.bookstore.book.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wat.bookstore.book.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book,String>{

}
