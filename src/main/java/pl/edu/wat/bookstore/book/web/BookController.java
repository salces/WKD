package pl.edu.wat.bookstore.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.bookstore.book.domain.Book;
import pl.edu.wat.bookstore.book.repository.BookLoanRepository;
import pl.edu.wat.bookstore.book.repository.BookRepository;
import pl.edu.wat.bookstore.book.service.LoanBookService;
import pl.edu.wat.bookstore.book.service.exceptions.NoSuchBookException;
import pl.edu.wat.bookstore.book.web.DTO.LoanBookDTO;
import pl.edu.wat.security.SecurityUtils;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;


    @Autowired
    LoanBookService loanBookService;

    @RequestMapping(method = GET)
    @ResponseStatus(code = OK)
    public Page<Book> getAllBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    @RequestMapping(value = "/count", method = GET)
    public long count(){
        return bookRepository.count();
    }

    @RequestMapping(value = "/loan", method = POST,consumes = "application/json")
    @ResponseStatus(code = OK)
    public void loanBook(@RequestBody LoanBookDTO loanBookDTO) {
        loanBookService.loan(loanBookDTO);
    }

    @RequestMapping(value = "/loan/payment", method = POST, consumes = "application/json")
    @ResponseStatus(code = OK)
    public void makePayment(@RequestBody String loanBookID){
        loanBookService.makePayment(loanBookID);
    }

    @RequestMapping(method = POST)
    @ResponseStatus(code = OK)
    public void add(@RequestBody Book book){
        bookRepository.insert(book);
    }

    @RequestMapping(method = PUT)
    @ResponseStatus(code = OK)
    public void edit(@RequestBody Book book){
        bookRepository.save(book);
    }
}
