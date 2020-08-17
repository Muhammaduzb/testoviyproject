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

    public void addBook(String name, String author, String language, String comment){
        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = user.getId();

        Book book = new Book();
        book.setName(name);
        book.setComment(comment);
        book.setPicByte(null);
//        book.setPicByte(compressBytes(multipartFile.getBytes()));
        book.setTimestamp(null);
        book.setAuthor(author);
        book.setLanguage(language);
        book.setUserId(id);
        System.out.println("book:" + book);
        bookRepository.save(book);

    }

    public void addUserAddress(Integer region,Integer district){
        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = user.getId();
        System.out.println("Id =" + id);
        addressRepository.save(new UserAddress(id,region,district));

    }

    // compress the image bytes before storing it in the database

    public static byte[] compressBytes(byte[] data) {

        Deflater deflater = new Deflater();

        deflater.setInput(data);

        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {

            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);

        }

        try {

            outputStream.close();

        } catch (IOException e) {

        }

        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();

    }


        // uncompress the image bytes before returning it to the angular application

    public static byte[] decompressBytes(byte[] data) {

        Inflater inflater = new Inflater();

        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];

        try {

            while (!inflater.finished()) {

                int count = inflater.inflate(buffer);

                outputStream.write(buffer, 0, count);

            }

            outputStream.close();

        } catch (IOException ioe) {

        } catch (DataFormatException e) {

        }

        return outputStream.toByteArray();

    }

}

