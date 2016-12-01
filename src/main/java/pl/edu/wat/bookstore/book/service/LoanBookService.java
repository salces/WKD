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
import pl.edu.wat.bookstore.book.service.exceptions.NoSuchLoanedBookException;
import pl.edu.wat.bookstore.book.web.DTO.InactiveBookLoanDTO;
import pl.edu.wat.bookstore.book.web.DTO.InputLoanBookDTO;
import pl.edu.wat.bookstore.book.web.DTO.OutputLoanBookDTO;
import pl.edu.wat.domain.User;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.security.SecurityUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanBookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookLoanRepository bookLoanRepository;

    public void loan(InputLoanBookDTO inputLoanBookDTO) {
        Book bookToLoan = bookRepository.findOne(inputLoanBookDTO.getIsbn());

        if (bookToLoan == null) {
            throw new NoSuchBookException(inputLoanBookDTO.getIsbn());
        }

        User owner = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        if (owner == null) {
            throw new UsernameNotFoundException("No such user with login " + owner);
        }

        boolean isAlreadyLoaned = !owner
            .getBookLoans()
            .stream()
            .filter(b -> b.isActive())
            .filter(b -> b.getISBN().equals(inputLoanBookDTO.getIsbn()))
            .collect(Collectors.toList())
            .isEmpty();
        if (isAlreadyLoaned) {
            throw new BookAlreadyLoanedException(inputLoanBookDTO.getIsbn(), owner.getLogin());
        }

        BookLoan bookLoan = BookLoan.builder()
            .ISBN(inputLoanBookDTO.getIsbn())
            .days(inputLoanBookDTO.getDays())
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

    public List<OutputLoanBookDTO> getLoanedBooksDTOs(String username) {
        User owner = userRepository.findOneByLogin(username).get();

        if (owner == null) {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

        List<BookLoan> loanedBooks = bookLoanRepository.findByOwner(owner);
        List<OutputLoanBookDTO> books = new ArrayList<>();

        loanedBooks.forEach(bookLoan -> {
            Book book = bookRepository.findOne(bookLoan.getISBN());
            books.add(OutputLoanBookDTO
                .builder()
                .book(book)
                .isActive(bookLoan.isActive())
                .isPaid(bookLoan.isPaid())
                .startDate(bookLoan.getStartDate())
                .endDate(bookLoan.getEndDate())
                .build());
        });

        return books;
    }


    public List<InactiveBookLoanDTO> getInactiveBookLoans() {
        List<BookLoan> bookLoans = bookLoanRepository.findByIsPaid(false);
        List<InactiveBookLoanDTO> inactiveBookLoanDTOs = new ArrayList<>();

        bookLoans.forEach(bookLoan -> {
            System.out.println(bookLoan.getISBN());
            Book book = bookRepository.findOne(bookLoan.getISBN());
            inactiveBookLoanDTOs.add(InactiveBookLoanDTO
                .builder()
                .book(book)
                .user(bookLoan.getOwner().getLogin())
                .bookLoanID(bookLoan.getID())
                .build());
        });

        return inactiveBookLoanDTOs;
    }

    public void activate(String bookLoanID) {
        BookLoan bookLoan = bookLoanRepository.findOne(bookLoanID);
        activateLoanBook(bookLoan);
        bookLoanRepository.save(bookLoan);
    }

    private void activateLoanBook(BookLoan bookLoan) {
        bookLoan.setActive(true);
        bookLoan.setPaid(true);
        bookLoan.setStartDate(getToday());
        bookLoan.setEndDate(getDateAfterNDays(bookLoan.getDays()));
    }

    private Date getDateAfterNDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    private Date getToday() {
        return new Date();
    }
}
