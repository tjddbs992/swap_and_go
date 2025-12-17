package com.swapandgo.sag.config;

import com.swapandgo.sag.domain.user.Address;
import com.swapandgo.sag.domain.user.User;
import com.swapandgo.sag.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component

public class DummyUserInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DummyUserInit(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("jangeh3031@naver.com").isEmpty()) {
            String encodedPassword = passwordEncoder.encode("password");
            Address address = new Address("Germany", "Ulm", "Syrlinstrasse 8");
            User user = User.createUser("장은호", "jangeh3031@naver.com", encodedPassword, address);
            userRepository.save(user);
        }
    }
}
