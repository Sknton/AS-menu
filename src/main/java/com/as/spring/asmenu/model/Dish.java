package com.as.spring.asmenu.model;


import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "dish")
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

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(name = "dish_basket",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_id"))
    private Collection<Basket> baskets;

    public Dish() {
    }

    public Dish(String name, String type, String fileName, String description, String composition) {
        this.name = name;
        this.type = type;
        this.fileName = fileName;
        this.description = description;
        this.composition = composition;
    }

    public Dish(String name,
                String type,
                String fileName,
                String description,
                String composition,
                Collection<Basket> baskets) {
        this.name = name;
        this.type = type;
        this.fileName = fileName;
        this.description = description;
        this.composition = composition;
        this.baskets = baskets;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Collection<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(Collection<Basket> baskets) {
        this.baskets = baskets;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", fileName='" + fileName + '\'' +
                ", description='" + description + '\'' +
                ", composition='" + composition + '\'' +
                '}';
    }
}
