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
@Table(name = "user_order")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotNull(message = "is required")
    @Size(min = 10, message = "is required")
    @Column(name = "address")
    private String address;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<OrderDish> orderDishes;

    public Order(String address) {
        this.address = address;
    }

    public Order(String address, User user) {
        this.address = address;
        this.user = user;
    }

    public Order(String address, User user, List<OrderDish> orderDishes) {
        this.address = address;
        this.user = user;
        this.orderDishes=orderDishes;
    }
}
