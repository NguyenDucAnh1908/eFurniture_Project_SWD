package com.eFurnitureproject.eFurniture;

import com.cloudinary.Cloudinary;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.models.Enum.Role;
import com.eFurnitureproject.eFurniture.services.IUserService;
import com.eFurnitureproject.eFurniture.services.JwtService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class EFurnitureApplication {

    public static void main(String[] args) {
        SpringApplication.run(EFurnitureApplication.class, args);
    }

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap<>();
        config.put("cloud_name", "dwqq0mx4j");
        config.put("api_key", "485134685654857");
        config.put("api_secret", "lFJj6FmcE2owKBgm_1UGvb4-m6M");
        cloudinary = new Cloudinary(config);
        return cloudinary;

        //DucAnh
//		config.put("cloud_name", "dbzzkebfm");
//		config.put("api_key", "491474858821112");
//		config.put("api_secret", "FRYILcI0JEt7BcVnrr2BecTBlak");

    }

    @Bean
    public CommandLineRunner commandLineRunner(
            IUserService userService

    ) {
        return args -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            UserDto adminRequest = UserDto.builder()
                    .fullName("Admin")
                    .dateOfBirth(dateFormat.parse("2024-2-20"))
                    .phoneNumber("0937534654")
                    .email("admin@gmail.com")
                    .password("admin")
                    .role(Role.ADMIN)
                    .build();
            userService.createUser(adminRequest);
        };

    }
}