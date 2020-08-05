package uz.pdp.userregistertest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.userregistertest.entity.User;
import uz.pdp.userregistertest.model.Result;
import uz.pdp.userregistertest.payload.ActivationRequest;
import uz.pdp.userregistertest.payload.RegisterReq;
import uz.pdp.userregistertest.repository.UserRepository;
import uz.pdp.userregistertest.service.AuthService;
import uz.pdp.userregistertest.service.UserService;


import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    private UserRepository userRepository;
    private UserDetailsService userDetailsService;

    @GetMapping(value = {"/login"})
    public String getHomePage() {
        return "login";
    }

     @GetMapping(value = {"/activation"})
        public String getActivationPage() {
            return "activation";
        }

    @GetMapping(value = {"/sign/up"})
    public String getRegisterPage(Model model, RegisterReq registerReq) {
        model.addAttribute("registerReq", registerReq);
        return "register";
    }


    @PostMapping(value = {"/sign/up"})
    public String registerUser(@Valid RegisterReq registerReq,
                               BindingResult bindingResult,
                               Model model) {
        model.addAttribute("registerReq", registerReq);
//        Optional<String> optionalS = SecurityUtils.getCurrentUser();

//        System.out.println("user : " + optionalS.get().toString());
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            userService.saveUser(registerReq);
            return "activation";
//            return "activation";
        }
    }

//    @GetMapping("/activate/user")
//    public String activeUser(Model model,ActivationRequest activationRequest)
//    {
//        model.addAttribute("activationReq",activationRequest);
//        return "activation";
//    }

    @GetMapping(value = {"/activate/user"})
    public String getActivePage(Model model,ActivationRequest activationRequest) {
            model.addAttribute("activationReq",activationRequest);
            return "activation";

    }

    @PostMapping(value = {"/wActivate/user"})
    public String getActiveUser(@Valid ActivationRequest activationRequest,BindingResult bindingResult,
                Model model) {
        Result result = authService.activateUser(activationRequest.getActiveCode());
        model.addAttribute("activationReq",activationRequest);
        if (!bindingResult.hasErrors() && result.isSuccess()) {
            return "redirect:/cabinet";
        } else {
            return "activation";
        }
    }
//    public String getActivePage(@RequestParam(name = "activeCode") String activeCode) {
//        Result result = authService.activateUser(activeCode);
//        if (result.isSuccess()) {
//            return "redirect:/cabinet";
//        } else {
//            return "activation";
//        }
//
//    }

   }

