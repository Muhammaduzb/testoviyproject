package uz.pdp.userregistertest.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.userregistertest.entity.Role;
import uz.pdp.userregistertest.entity.User;
import uz.pdp.userregistertest.model.Result;
import uz.pdp.userregistertest.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.
                findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
//        User user = userRepository.findByUsername(username).get();
//            List<Role> grantedAuthorities = user.getAuthorities().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList()); // (1)
//            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities); // (2)

    }

    public Result activateUser(String activeCode) {
        Result result = new Result();
        if (userRepository.findByActivCode(activeCode).isPresent()) {
            User user = userRepository.findByActivCode(activeCode).get();
            user.setEnabled(true);
            user.setActivCode(null);
            System.out.println("Username : " + user.getUsername());
            System.out.println("Password : " + user.getPassword());
            userRepository.save(user);
            authWithoutPassword(user);
            result.setSuccess(true);
            }
        else {
            result.setSuccess(false);
        }
        return result;
    }

    public void authWithoutPassword(User user) {

        List<GrantedAuthority> roles = (List<GrantedAuthority>) user.getAuthorities();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
