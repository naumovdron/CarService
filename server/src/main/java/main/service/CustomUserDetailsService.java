package main.service;

import main.entity.User;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public User register(User user) {
        List<String> userRoles = Collections.singletonList("ROLE_USER");
        user.setRoles(userRoles);
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
