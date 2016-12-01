package pl.edu.wat.bookstore.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.edu.wat.bookstore.book.repository.BookLoanRepository;

@Service
public class InactivateScheduler {

    @Autowired
    BookLoanRepository bookLoanRepository;
    
    @Scheduled(cron = "0 0 0 * * *")
    public void inactivate(){
        bookLoanRepository.findAll().forEach(b -> {
            if(b.isOutOfDate()){
                b.setActive(false);
                bookLoanRepository.save(b);
            }
        });
    }
}
