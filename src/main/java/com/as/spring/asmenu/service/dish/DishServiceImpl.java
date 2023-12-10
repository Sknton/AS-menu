package com.as.spring.asmenu.service.dish;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.BasketDish;
import com.as.spring.asmenu.model.Dish;
import com.as.spring.asmenu.repository.BasketDishRepository;
import com.as.spring.asmenu.repository.BasketRepository;
import com.as.spring.asmenu.repository.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    private final BasketRepository basketRepository;

    private final BasketDishRepository basketDishRepository;

    @Value("${upload.path.menu}")
    private String uploadPath;

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Set<String> getUniqueDishTypes() {
        List<Dish> dishes = dishRepository.findAll();

        Set<String> types = new HashSet<>();
        for (Dish dish : dishes) {
            types.add(dish.getType());
        }

        return types;
    }

    @Override
    public void save(Dish dish, MultipartFile file) {
        // Save the image file and set the filename in the dish object
        try {
            if (!file.isEmpty()){
                String fileName = saveImageFile(file);
                dish.setFileName(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately in your application
        }

        dishRepository.save(dish);
    }

    private String saveImageFile(MultipartFile imageFile) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + imageFile.getOriginalFilename();
        imageFile.transferTo(new File(uploadPath + "/" + resultFilename));
        return resultFilename;
    }

    @Override
    public void deleteById(Long id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id: " + id));

        deleteDishesInBasket(dish);
        deleteDishImage(uploadPath+"/"+dish.getFileName());
        dishRepository.deleteById(id);
    }

    private void deleteDishesInBasket(Dish dish) {
        List<Basket> baskets = dish.getBasketDishes().stream()
                .map(BasketDish::getBasket)
                .collect(Collectors.toList());
        for (Basket basket : baskets) {
            Integer quantity = basketDishRepository.findByBasketAndDish(basket, dish).getQuantity();
            basket.setQuantity(basket.getQuantity() - quantity);

            basket.setTotalPrice(basket.getTotalPrice() -
                    quantity * dish.getPrice());
        }
        basketRepository.saveAll(baskets);
    }

    private void deleteDishImage(String path){
        try {
            File file = new File(path);
            file.delete();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Dish findById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id: " + id));
    }
}
