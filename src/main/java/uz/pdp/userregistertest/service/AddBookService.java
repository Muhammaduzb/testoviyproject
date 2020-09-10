package uz.pdp.userregistertest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.userregistertest.entity.Book;
import uz.pdp.userregistertest.entity.User;
import uz.pdp.userregistertest.entity.UserAddress;
import uz.pdp.userregistertest.model.Result;
import uz.pdp.userregistertest.repository.BookRepository;
import uz.pdp.userregistertest.repository.UserAddressRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
            System.out.println("book:" + book);
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
        System.out.println("Id =" + id);
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

