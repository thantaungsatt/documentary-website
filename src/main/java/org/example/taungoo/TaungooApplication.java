package org.example.taungoo;

import org.example.taungoo.dao.UserDao;
import org.example.taungoo.entity.Role;
import org.example.taungoo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TaungooApplication {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner run(){
        return r->{
            User user=
                    new User(
                            "tas",
                            passwordEncoder.encode("12345"),
                            "tas@gmail.com",
                            "09763637172",
                            Role.ROLE_ADMIN
                    );
            userDao.save(user);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(TaungooApplication.class, args);
    }

}
