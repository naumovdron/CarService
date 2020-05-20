package main.web;

import main.dto.AuthRequest;
import main.dto.RegistrationRequest;
import main.entity.User;
import main.security.jwt.JwtTokenProvider;
import main.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SecurityController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest request) {
        try {
            String username = request.getUserName();
            String password = request.getPassword();
            User user = userDetailsService.getByUsername(username);
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Invalid username or password");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("userName", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody RegistrationRequest request) {
        String username = request.getUserName();
        String password = request.getPassword();
        String passwordConfirm = request.getPasswordConfirm();
        if (password.equals(passwordConfirm)) {
            try {
                userDetailsService.getByUsername(username);
                throw new BadCredentialsException("Invalid username or password");
            } catch (UsernameNotFoundException e) {
                User user = userDetailsService.register(new User(username, passwordEncoder.encode(password), null));
                String token = jwtTokenProvider.createToken(username, user.getRoles());
                Map<Object, Object> response = new HashMap<>();
                response.put("userName", username);
                response.put("token", token);
                return ResponseEntity.ok(response);
            }
        } else {
            throw new BadCredentialsException("Password mismatch");
        }
    }
}
