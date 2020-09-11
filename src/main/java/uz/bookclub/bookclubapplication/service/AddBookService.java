package uz.bookclub.bookclubapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.bookclub.bookclubapplication.entity.Book;
import uz.bookclub.bookclubapplication.entity.User;
import uz.bookclub.bookclubapplication.entity.UserAddress;
import uz.bookclub.bookclubapplication.repository.BookRepository;
import uz.bookclub.bookclubapplication.repository.UserAddressRepository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * created by Muhammad
 * on 13.08.2020
 */

@Service
public class AddBookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserAddressRepository addressRepository;

    public void addBook(byte[] file,String name, String author, Integer language,String comment,Integer id){

        Date date= new Date();
        long time = date.getTime();

        Timestamp ts = new Timestamp(time);

        Book book = new Book();

        try {
            book.setName(name);

            book.setComment(comment);
            book.setPhoto(file);
            book.setTimestamp(ts);
            book.setAuthor(author);
            book.setLanguage(language);
            book.setUserId(id);
//            System.out.println("book:" + book);
            bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUserAddress(Integer region,Integer district){
        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = user.getId();
//        System.out.println("Id =" + id);
        try {
            if (addressRepository.findById(id).isPresent()){
                addressRepository.deleteById(id);
                addressRepository.save(new UserAddress(id,region,district));
            }else {
                addressRepository.save(new UserAddress(id, region, district));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

