package main;

import main.entity.Car;
import main.entity.User;
import main.repository.CarRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        //carRepository.save(new Car("12abc3", "Kia", "red", true));
        //carRepository.save(new Car("45def6", "Lada", "brown", false));

//        List<String> roles = new ArrayList<>();
//        roles.add("ROLE_USER");
//        userRepository.save(new User("user", passwordEncoder.encode("pwd"), roles));
//        roles.add("ROLE_ADMIN");
//        userRepository.save(new User("admin", passwordEncoder.encode("admin"), roles));
    }
}
