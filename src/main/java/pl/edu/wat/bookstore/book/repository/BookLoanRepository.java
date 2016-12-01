package pl.edu.wat.bookstore.book.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wat.bookstore.book.domain.BookLoan;
import pl.edu.wat.domain.User;

import java.util.List;

public interface BookLoanRepository extends MongoRepository<BookLoan,String>{
    List<BookLoan> findByOwner(User owner);
    List<BookLoan> findByIsPaid(boolean isPaid);
}
