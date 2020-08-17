package uz.pdp.userregistertest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.userregistertest.entity.*;
import uz.pdp.userregistertest.model.Result;
import uz.pdp.userregistertest.repository.BookRepository;
import uz.pdp.userregistertest.service.AddBookService;
import uz.pdp.userregistertest.service.RegionDistrictService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Muhammad
 * on 13.08.2020
 */

@RestController
public class AddBookController {

    @Autowired
    RegionDistrictService regionDistrictService;

    @Autowired
    AddBookService addBookService;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/regions")
    @ResponseBody
    public List<Region> getregions() {
        System.out.println(regionDistrictService.getAllRegions());
        return regionDistrictService.getAllRegions();
    }

    @GetMapping("/districts")
    @ResponseBody
    public List<District> getDistricts(HttpServletRequest request) {
        Integer regionId = Integer.parseInt(request.getParameter("regionId"));
        System.out.println(regionDistrictService.getAllDistricts(regionId));
        return regionDistrictService.getAllDistricts(regionId);
    }

    @GetMapping("/districts/{region}/{district}")
    public void addUserAddress(@PathVariable("region") Integer region,@PathVariable("district") Integer district){
         addBookService.addUserAddress(region,district);
            }

    @GetMapping("/addBook/{name}/{author}/{language}/{comment}")
        public void addBook(@PathVariable("name") String name,@PathVariable("author") String author,
                            @PathVariable("language") String language,@PathVariable("comment") String comment){
             addBookService.addBook(name,author,language,comment);
                }
//    @GetMapping("/addBook/{name}/{author}/{language}/{comment}/{file}")
//            public void addBook(@PathVariable("name") String name,@PathVariable("author") String author,
//                                @PathVariable("language") String language,@PathVariable("comment") String comment,
//                                @PathVariable("file") MultipartFile file) throws IOException {
//                 addBookService.addBook(name,author,language,comment,file);
//                    }

    @GetMapping("/actionBook/myBooks")
    @ResponseBody
    public List<Book> getMyBooks(){


        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = user.getId();

        return bookRepository.getMyBooks(id);
    }
}
