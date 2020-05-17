package main;

import main.entity.Application;
import main.entity.User;
import main.repository.ApplicationRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        applicationRepository.save(new Application("FirstApp", "My first app"));
        applicationRepository.save(new Application("SecondApp", "My second app"));

        userRepository.save(new User("user", passwordEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("admin", passwordEncoder.encode("apwd"), Collections.singletonList("ROLE_ADMIN")));
    }
}
