package com.ismail.products.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data @ToString @AllArgsConstructor
@NoArgsConstructor @Entity
public class Product implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=6,max=70)
    private String designation;
    @DecimalMin("50")
    private double price;
    private int quantity;
}
