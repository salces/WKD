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

import java.time.LocalDate;
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
        Book bookToLoan = bookRepository.findOne(loanBookDTO.getISBN());

        if(bookToLoan == null){
            throw new NoSuchBookException(loanBookDTO.getISBN());
        }

        User owner = userRepository.findOne(loanBookDTO.getUserID());

        if(owner == null){
            throw new UsernameNotFoundException("No such user with login " + loanBookDTO.getUserID());
        }

        boolean isAlreadyLoaned = !owner
                                    .getBookLoans()
                                    .stream()
                                    .filter(b -> b.isActive())
                                    .filter(b -> b.getISBN().equals(loanBookDTO.getISBN()))
                                    .collect(Collectors.toList())
                                    .isEmpty();
        if(isAlreadyLoaned){
            throw new BookAlreadyLoanedException(loanBookDTO);
        }

        BookLoan bookLoan = BookLoan.builder()
                                .ISBN(loanBookDTO.getISBN())
                                .days(loanBookDTO.getDays())
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(loanBookDTO.getDays()))
                                .isActive(true)
                                .owner(owner)
                                .build();

        owner.addBookLoan(bookLoan);
        bookLoanRepository.insert(bookLoan);
        userRepository.save(owner);
    }
}
