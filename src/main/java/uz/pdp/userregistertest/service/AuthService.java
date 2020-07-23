package uz.pdp.userregistertest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.userregistertest.entity.User;
import uz.pdp.userregistertest.model.Result;
import uz.pdp.userregistertest.repository.UserRepository;

import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.
                findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Result activate(String activCode) {
        Result result = new Result();
        if (userRepository.findByActivCode(activCode).isPresent()) {
            User user = userRepository.findByActivCode(activCode).get();
            user.setEnabled(true);
            user.setActivCode(null);
            userRepository.save(user);
            authWithoutPassword(user);
            result.setSuccess(true);
        } else {
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
