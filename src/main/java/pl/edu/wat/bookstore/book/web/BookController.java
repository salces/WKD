package pl.edu.wat.bookstore.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.bookstore.book.domain.Book;
import pl.edu.wat.bookstore.book.repository.BookRepository;
import pl.edu.wat.bookstore.book.service.LoanBookService;
import pl.edu.wat.bookstore.book.service.exceptions.NoSuchBookException;
import pl.edu.wat.bookstore.book.web.DTO.LoanBookDTO;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    LoanBookService loanBookService;

    @RequestMapping(method = GET)
    @ResponseStatus(code = OK)
    public Page<Book> GetAllBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    @RequestMapping(value = "/count", method = GET)
    public long Count(){
        return bookRepository.count();
    }

    @RequestMapping(value = "/loan", method = POST)
    @ResponseStatus(code = OK)
    public void LoanBook(LoanBookDTO loanBookDTO) throws NoSuchBookException {
        loanBookService.loan(loanBookDTO);
    }
}