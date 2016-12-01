package pl.edu.wat.bookstore.book.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.edu.wat.bookstore.book.domain.BookLoan;
import pl.edu.wat.bookstore.book.repository.BookLoanRepository;
import pl.edu.wat.service.MailService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailScheduler {

    @Autowired
    MailService mailService;

    @Autowired
    BookLoanRepository bookLoanRepository;

    private final String message = "Your book with ISBN %s and ID of order %s is going to end today! Just saying ...";

    @Scheduled(cron = "0 0 0 * * *")
    public void sendNotificationAboutEndOfLoan() {
        List<BookLoan> bookLoansNearToEnd = getEndOfLoanBooks();

        bookLoansNearToEnd.forEach(
            bookLoan ->
                mailService.sendEmail(
                    bookLoan.getOwner().getEmail()
                    , "Your book loan is going to end!"
                    , String.format(message, bookLoan.getISBN(), bookLoan.getID())
                    , false
                    , false
                )
        );
    }

    private List<BookLoan> getEndOfLoanBooks() {
        List<BookLoan> bookLoansNearEnd = new ArrayList<>();

        bookLoanRepository
            .findAll()
            .forEach(bookLoan -> {
                if (bookLoan.isNearToEnd())
                    bookLoansNearEnd.add(bookLoan);
            });
        return bookLoansNearEnd;
    }
}
