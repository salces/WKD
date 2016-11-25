package pl.edu.wat.bookstore.book.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wat.bookstore.book.domain.BookLoan;

public interface BookLoanRepository extends MongoRepository<BookLoan,Long>{
}
