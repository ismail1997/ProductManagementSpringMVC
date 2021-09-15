package com.ismail.products;

import com.ismail.products.dao.ProductRepository;
import com.ismail.products.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductsApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;
    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        productRepository.findAll().forEach(System.out::println);
    }
}
