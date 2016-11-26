package pl.edu.wat.bookstore.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wat.bookstore.book.domain.Book;
import pl.edu.wat.bookstore.book.domain.BookLoan;
import pl.edu.wat.bookstore.book.repository.BookLoanRepository;
import pl.edu.wat.bookstore.book.repository.BookRepository;
import pl.edu.wat.bookstore.book.service.exceptions.BookAlreadyLoanedException;
import pl.edu.wat.bookstore.book.service.exceptions.NoSuchBookException;
import pl.edu.wat.bookstore.book.web.DTO.LoanBookDTO;
import pl.edu.wat.domain.User;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.security.SecurityUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class LoanBookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookLoanRepository bookLoanRepository;

    public void loan(LoanBookDTO loanBookDTO) {
        Book bookToLoan = bookRepository.findOne(loanBookDTO.getIsbn());

        if(bookToLoan == null){
            throw new NoSuchBookException(loanBookDTO.getIsbn());
        }

        User owner = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        if(owner == null){
            throw new UsernameNotFoundException("No such user with login " + owner);
        }

        boolean isAlreadyLoaned = !owner
                                    .getBookLoans()
                                    .stream()
                                    .filter(b -> b.isActive())
                                    .filter(b -> b.getISBN().equals(loanBookDTO.getIsbn()))
                                    .collect(Collectors.toList())
                                    .isEmpty();
        if(isAlreadyLoaned){
            throw new BookAlreadyLoanedException(loanBookDTO.getIsbn(),owner.getLogin());
        }

        BookLoan bookLoan = BookLoan.builder()
                                .ISBN(loanBookDTO.getIsbn())
                                .days(loanBookDTO.getDays())
                                .startDate(new Date())
                                .endDate(new Date())
                                .isActive(false)
                                .isPaid(false)
                                .owner(owner)
                                .build();

        owner.addBookLoan(bookLoan);
        bookLoanRepository.insert(bookLoan);
        userRepository.save(owner);
    }
}
