package main;

import main.entity.User;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInit implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        userRepository.findByUsername("admin").orElseGet(() -> new User("admin",
                passwordEncoder.encode("admin"),
                Collections.singletonList("ROLE_ADMIN")));
    }
}
