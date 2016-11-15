package pl.edu.wat.bookstore.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.bookstore.book.domain.Book;
import pl.edu.wat.bookstore.book.repository.BookRepository;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/book")
public class BookResource {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(method = GET)
    public Page<Book> GetAllBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    @RequestMapping(value = "/count", method = GET)
    public long Count(){
        return bookRepository.count();
    }
}
