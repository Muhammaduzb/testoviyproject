package uz.bookclub.bookclubapplication.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uz.bookclub.bookclubapplication.DAO.BookDAO;
import uz.bookclub.bookclubapplication.DAO.BookDaoDefault;
import uz.bookclub.bookclubapplication.entity.Book;
import uz.bookclub.bookclubapplication.entity.User;
import uz.bookclub.bookclubapplication.repository.*;
import uz.bookclub.bookclubapplication.security.SignedUser;
import uz.bookclub.bookclubapplication.service.GetBookService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class CabinetController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    GetBookService bookService;

    @GetMapping({"/cabinet"})
    public String getCabinetPage(Model model) {

        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
//        user.setUsername(user.getUsername().replace("-","")
//                .replace(" ","").replace("+",""));

        //        passwordEncoder.encode(user.getPassword());
//        new User();
//        List<Regions> regions = regionService.getAllRegions();

        System.out.println(user);
//        model.a
        model.addAttribute("user", user);
//        model.addAttribute("regions",regions);
        return "cabinet";
    }
//    @GetMapping({"/", "/cabinet"})
//    public String getCabinetPage(Model model) {
//        User user = new User();
//
//        user.setUsername(user.getUsername().replace("-","")
//                .replace(" ","").replace("+",""));
//
//        user =  (User) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//        System.out.println(user);
//        model.addAttribute("user", user);
//        return "cabinet";
//    }

    @GetMapping("/cabinet/about")
    public String getDeveloper(Model model, @SignedUser User user) {
//        User user = (User) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
        model.addAttribute("user", user);
        return "cabinet/about";
    }


    @GetMapping("/cabinet/users/list")
    @ResponseBody
    public List<User> getUsers() {
       return userRepository.findAll();
    }

    @GetMapping("/cabinet/userlist")
    public String getUserList(Model model, @SignedUser User user) {
        model.addAttribute("user", user);
        return "/cabinet/userlist";
    }

     @GetMapping("/actionBook/editBook/{id}")
        public String editBook(Model model,@PathVariable("id")Integer id) {
         BookDAO bookDAO = bookService.getMyBook(id);
         model.addAttribute("forEditBook", bookDAO);
            return "editBook";
        }

    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserAddressRepository userAddressRepository;
    @Autowired
    RegionRepo regionRepo;
    @Autowired
    DistrictRepo districtRepo;

    @GetMapping("/")
    public String listAllBook(Model model,@RequestParam(value = "page",
      required = false,defaultValue = "0") Integer page){
          Page<Book> pageBook = bookRepository.findAll(
                  PageRequest.of(page,6));
          List<Book> list = pageBook.getContent();
          List<BookDaoDefault> bookDAOList = new ArrayList<>();
        String language = null;
        for (int i = 0; i < list.size(); i++) {
           Integer id =  list.get(i).getUserId();
            String username = userRepository.findById(id).get().getUsername();
            Integer regionId = userAddressRepository.getMyRegionId(id).getRegionId();
            String region = regionRepo.getMyRegionName(regionId).getName();
            Integer districtId = userAddressRepository.getMyRegionId(id).getDistrictId();
            String district = districtRepo.getMyDistrictName(districtId).getName();
//            byte[] picture = list.get(i).getPhoto();

            byte[] encodeBase64 = Base64.encodeBase64(list.get(i).getPhoto());
            String picture = null;
            try {
                picture = new String(encodeBase64, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            System.out.println("list size " + i + " " + list.get(i));
            if (list.get(i).getLanguage() == 0) {
                language = "O'zbek";
            } else if (list.get(i).getLanguage() == 1) {
                language = "Rus";
            } else if (list.get(i).getLanguage() == 2) {
                language = "Kiril";
            } else if (list.get(i).getLanguage() == 3) {
                language = "English";
            } else if (list.get(i).getLanguage() == 4) {
                language = "Boshqa";
            }

            bookDAOList.add(i,new BookDaoDefault(list.get(i).getId(),picture,username,
                    list.get(i).getName(),list.get(i).getAuthor(),language,list.get(i).getComment(),district,region));
        }
        final Page<BookDaoDefault> bookDAOPage = new PageImpl<>(bookDAOList);

          model.addAttribute("bookPage",bookDAOPage);
//          model.addAttribute("userAddress",userAddresses);
          model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());
        return "cabinet";
      };
//    @PutMapping("/cabinet/user/edit/{id}")
//    @ResponseBody
//    public Result editUser(@Valid @RequestBody UserReq userReq,
//                           @PathVariable(value = "id") Integer id){
//        Result result=new Result();
//        Optional<User> optional=userRepository.findById(id);
//        if (optional.isPresent()){
//            User editedUser=optional.get();
//            editedUser.setFullName(userReq.getFullName());
//            List<Role>roles=new ArrayList<>();
//            if (userReq.isRoleAdmin()){
//                roles.add(roleRepository.getByName("ROLE_ADMIN"));
//            }
//            if (userReq.isRoleModer()){
//                roles.add(roleRepository.getByName("ROLE_MODER"));
//            }
//            if (userReq.isRoleUser()){
//                roles.add(roleRepository.getByName("ROLE_USER"));
//            }
//            editedUser.setRoles(roles);
//            editedUser.setAccountNonLocked(userReq.isAccountNonLocked());
//            User savedUser=userRepository.save(editedUser);
//            if (savedUser!=null){
//                result.setSuccess(true);
//                result.setMessage("Successfully updated");
//            } else {
//                result.setSuccess(false);
//                result.setMessage("Error in updating");
//            }
//        }else {
//            result.setSuccess(false);
//            result.setMessage("Error in updating");
//        }
//        return result;
//    }
//    @DeleteMapping("/cabinet/user/delete/{id}")
//    @ResponseBody
//    public Result deleteUser(@PathVariable(value = "id") Integer id) {
//        userRepository.deleteById(id);
//        Optional<User> deletedUser =  userRepository.findById(id);
//        Result result = new Result();
//        if (!deletedUser.isPresent()) {
//            result.setSuccess(true);
//            result.setMessage("Successfully deleted");
//        } else {
//            result.setSuccess(false);
//            result.setMessage("Error in deleting");
//        }
//        return result;
//    }
//
//
//
//    @GetMapping("/admin")
//    public String getAdminPage() {
//        return "admin";
//    }
//
//    @GetMapping("/moderator")
//    public String getModerPage() {
//        return "moderator";
//    }
//
//    @GetMapping("/user")
//    public String getUserPage() {
//        return "user";
//    }
}


