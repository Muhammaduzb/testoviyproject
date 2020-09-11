package uz.bookclub.bookclubapplication.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bookclub.bookclubapplication.entity.Role;
import uz.bookclub.bookclubapplication.entity.User;
import uz.bookclub.bookclubapplication.model.Result;
import uz.bookclub.bookclubapplication.payload.RegisterReq;
import uz.bookclub.bookclubapplication.repository.RoleRepository;
import uz.bookclub.bookclubapplication.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Result saveUser(RegisterReq registerReq) {
        Result result = new Result();
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getByName("ROLE_USER"));
        String userUsername = registerReq.getUsername().replace("-","")
                .replace(" ","").replace("+","");
        Optional<User> optional=userRepository.findByUsername(userUsername);
//                System.out.println(optional.isPresent());
                if (optional.isPresent()) {
            result.setSuccess(false);
            result.setMessage("This username or email already exist");
        } else {
            String random_int = String.valueOf((Math.random() * (99999 - 10000 + 1) + 10000)).substring(0,4);

//            System.out.println("random:" + random_int);
            User user=new User(userUsername,
                    passwordEncoder.encode(registerReq.getPassword()),
                    random_int,
                    roles);
                    logger.debug(String.format("Auto login %s successfully!", user.getUsername()));
//                    System.out.println();
            userRepository.save(user);
            SendSms.sendSms(userUsername,random_int);
            result.setSuccess(true);
        }
        return result;
    }}


