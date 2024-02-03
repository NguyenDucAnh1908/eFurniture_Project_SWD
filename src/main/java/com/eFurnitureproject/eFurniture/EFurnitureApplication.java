package com.eFurnitureproject.eFurniture;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	}
}
