package uz.bookclub.bookclubapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.bookclub.bookclubapplication.DAO.BookDAO;
import uz.bookclub.bookclubapplication.entity.*;
import uz.bookclub.bookclubapplication.repository.BookRepository;
import uz.bookclub.bookclubapplication.service.AddBookService;
import uz.bookclub.bookclubapplication.service.DeleteBookService;
import uz.bookclub.bookclubapplication.service.GetBookService;
import uz.bookclub.bookclubapplication.service.RegionDistrictService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * created by Muhammad
 * on 13.08.2020
 */

@RestController
public class BookRestController {

    @Autowired
    RegionDistrictService regionDistrictService;

    @Autowired
    AddBookService addBookService;

    @Autowired
    GetBookService getBookService;

    @Autowired
    DeleteBookService deleteBookService;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/regions")
    @ResponseBody
    public List<Region> getregions() {
//        System.out.println(regionDistrictService.getAllRegions());
        return regionDistrictService.getAllRegions();
    }

    @GetMapping("/districts")
    @ResponseBody
    public List<District> getDistricts(HttpServletRequest request) {
        Integer regionId = Integer.parseInt(request.getParameter("regionId"));
//        System.out.println(regionDistrictService.getAllDistricts(regionId));
        return regionDistrictService.getAllDistricts(regionId);
    }

    @GetMapping("/districts/{region}/{district}")
    public void addUserAddress(@PathVariable("region") Integer region,@PathVariable("district") Integer district){
         addBookService.addUserAddress(region,district);
            }

    @PostMapping("/actionBook/addBook/{name}/{author}/{language}/{comment}")
    public void addBook(final @RequestParam("file") MultipartFile file,final @PathVariable("name") String name,
                        final @PathVariable("author") String author,final @PathVariable("language") Integer language,
                        final @PathVariable("comment") String comment) throws IOException {
        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = user.getId();
//        System.out.println(file.getBytes()+ "--------------  " + name + author + language + comment +id);
        addBookService.addBook(file.getBytes(),name.toLowerCase(),author.toLowerCase(),language,comment,id);
                    }

    @GetMapping("/actionBook/myBooks")
    @ResponseBody
    public List<BookDAO> getMyBooks(){
        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = user.getId();
        return getBookService.getMyBooks(id);
    }


    @GetMapping("/actionBook/allBooks")
    @ResponseBody
    public List<BookDAO> getAllBooks(){
        return getBookService.getAllBooks();
    }

    @PostMapping("/actionBook/deleteMyBook/{id}")
    public void deleteMyBook(@PathVariable("id")Integer id){
        deleteBookService.deleteBook(id);
    }

    @GetMapping("/actionBook/book/{word}")
    @ResponseBody
    public List<BookDAO> getBooks(@PathVariable("word") String word){
        return getBookService.getSearchedBooks(word);
    }

    @GetMapping("/actionBook/region")
    @ResponseBody
    public UserAddress userAddress(){
        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = user.getId();
        return regionDistrictService.userAddress(id);
    }



}
