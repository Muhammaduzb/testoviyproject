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

//    @PostMapping("/addBook")
//        public void addBook(HttpServletRequest httpServletRequest){
////             addBookService.addBook(name,author,language,comment);
//        String name = httpServletRequest.getParameter("name");
//        String author = httpServletRequest.getParameter("author");
//        String comment = httpServletRequest.getParameter("comment");
//        String language = httpServletRequest.getParameter("language");
//
//
//        System.out.println("Hhtp request" + httpServletRequest);
//                }

    @PostMapping("/actionBook/addBook/{name}/{author}/{language}/{comment}")
    public void addBook(final @RequestParam("file") MultipartFile file,final @PathVariable("name") String name,
                        final @PathVariable("author") String author,final @PathVariable("language") Integer language,
                        final @PathVariable("comment") String comment) throws IOException {
        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = user.getId();
        System.out.println(file.getBytes()+ "--------------  " + name + author + language + comment +id);
        addBookService.addBook(file.getBytes(),name,author,language,comment,id);
                    }

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
