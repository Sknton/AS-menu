package com.as.spring.asmenu.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "is required")
    @Size(min = 5, max = 100, message = "must be between 5 and 100 characters")
    @Column(name = "name")
    private String name;

    @NotNull(message = "is required")
    @Size(min = 3, max = 20, message = "must be between 3 and 20 characters")
    @Column(name = "type")
    private String type;


    @Column(name = "file_name")
    private String fileName;

    @NotNull(message = "is required")
    @Size(min = 20, max = 255, message = "must be between 20 and 255 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "is required")
    @Size(min = 20, max = 255, message = "must be between 20 and 255 characters")
    @Column(name = "composition")
    private String composition;

    @NotNull(message = "is required")
    @Column(name = "price")
    private Double price;

    @ToString.Exclude
    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<BasketDish> basketDishes;

    @ToString.Exclude
    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<OrderDish> orderDishes;


    public Dish(String name, String type, String fileName, String description, String composition, Double price) {
        this.name = name;
        this.type = type;
        this.fileName = fileName;
        this.description = description;
        this.composition = composition;
        this.price = price;
    }



}
