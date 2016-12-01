package pl.edu.wat.bookstore.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.bookstore.book.domain.Book;
import pl.edu.wat.bookstore.book.repository.BookLoanRepository;
import pl.edu.wat.bookstore.book.repository.BookRepository;
import pl.edu.wat.bookstore.book.service.LoanBookService;
import pl.edu.wat.bookstore.book.web.DTO.InactiveBookLoanDTO;
import pl.edu.wat.bookstore.book.web.DTO.InputLoanBookDTO;
import pl.edu.wat.bookstore.book.web.DTO.OutputLoanBookDTO;
import pl.edu.wat.security.SecurityUtils;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookLoanRepository bookLoanRepository;

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
    public void loanBook(@RequestBody InputLoanBookDTO inputLoanBookDTO) {
        loanBookService.loan(inputLoanBookDTO);
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

    @RequestMapping(value = "/loaned",method = GET)
    @ResponseStatus(code = OK)
    public List<OutputLoanBookDTO> getLoaned(){
        String username = SecurityUtils.getCurrentUserLogin();
        return loanBookService.getLoanedBooksDTOs(username);
    }

    @RequestMapping(value = "/inactive", method = GET)
    @ResponseStatus(code = OK)
    public List<InactiveBookLoanDTO> getInactiveBookLoans(){
        return loanBookService.getInactiveBookLoans();
    }

    @RequestMapping(value = "/activate", method = POST)
    @ResponseStatus(code = OK)
    public void activate(@RequestBody String bookLoanID){
        loanBookService.activate(bookLoanID);
    }

    @RequestMapping(value= "/{ISBN}", method = DELETE)
    @ResponseStatus(code = OK)
    public void deleteBook(@PathVariable String ISBN){
        bookRepository.delete(ISBN);
    }
}
