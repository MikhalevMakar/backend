package ru.nsu.sber_portal.ccfit.services;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.models.dto.DishDto;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.mappers.DishMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;

import java.util.List;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class CategoryMenuService {

    private final CategoryMenuRepository categoryMenuRepository;

    private final DishRepository dishRepository;

    public List<DishDto> getListDishByCategory(Long id) {
        log.info("Get list by category_id and restaurant" + id);

        CategoryMenu categoryMenu = categoryMenuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("CategoryMenu not found with id: " + id));

      List<Dish> listDish = dishRepository.findByCategoryMenuIdAndRestaurantId(id, categoryMenu.getRestaurant().getId());

      log.info("List dish size " + listDish.size());
      return listDish.stream()
            .map(DishMapper::mapperToDto)
            .toList();
    }
}
