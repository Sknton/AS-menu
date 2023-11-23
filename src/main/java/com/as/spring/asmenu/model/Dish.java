package com.as.spring.asmenu.model;


import jakarta.persistence.*;
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

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "description")
    private String description;

    @Column(name = "composition")
    private String composition;

    @Column(name = "price")
    private Double price;

    @ToString.Exclude
    @OneToMany(mappedBy = "dish", fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private List<BasketDish> basketDishes;


    public Dish(String name, String type, String fileName, String description, String composition, Double price) {
        this.name = name;
        this.type = type;
        this.fileName = fileName;
        this.description = description;
        this.composition = composition;
        this.price = price;
    }

    public Dish(String name,
                String type,
                String fileName,
                String description,
                String composition,
                Double price,
                List<BasketDish> basketDishes) {
        this.name = name;
        this.type = type;
        this.fileName = fileName;
        this.description = description;
        this.composition = composition;
        this.price=price;
        this.basketDishes = basketDishes;
    }

}
