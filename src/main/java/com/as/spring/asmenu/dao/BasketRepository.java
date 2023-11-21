package com.as.spring.asmenu.dao;

import com.as.spring.asmenu.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {

}
