package uz.pdp.userregistertest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.userregistertest.entity.Book;
import uz.pdp.userregistertest.repository.BookRepository;

/**
 * created by Muhammad
 * on 24.08.2020
 */

@Service
public class DeleteBookService {
    @Autowired
    BookRepository bookRepository;

    public void deleteBook(Integer id){
        bookRepository.deleteById(id);
    }
}
