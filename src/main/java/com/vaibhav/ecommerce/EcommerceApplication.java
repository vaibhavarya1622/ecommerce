package com.vaibhav.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer(){
			@Override
			public void addCorsMapping(CorsRegistry registry){
				registry.addMapping("/").allowedOrigins("https://tranquil-sea-78476.herokuapp.com/");
			}
		}
	}
}
