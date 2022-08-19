package com.blog.coffee_shop.controller;

import com.blog.coffee_shop.dto.MenuItemAddDTO;
import com.blog.coffee_shop.model.MenuItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.blog.coffee_shop.util.Constant.UserRole.UserAuthorities.ADMIN;
import static com.blog.coffee_shop.util.Constant.UserRole.UserAuthorities.SUPER_ADMIN;
import static com.blog.coffee_shop.util.Constant.UserRole.UserAuthorities.USER;

@RestController
@RequestMapping("/api/menu")
public class CoffeeMenuController {

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority(\"" + SUPER_ADMIN + "\",\"" + ADMIN + "\",\"" + USER + "\")")
    public ResponseEntity<List<MenuItem>> getMenuItems() {
        final List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1, "Latte", new ArrayList<>()));
        menuItems.add(new MenuItem(2, "Cappuccino", new ArrayList<>()));
        menuItems.add(new MenuItem(3, "Americano", new ArrayList<>()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuItems);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority(\"" + SUPER_ADMIN + "\",\"" + ADMIN + "\")")
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody final MenuItemAddDTO menuItemAddDTO) {

        final MenuItem menuItem = new MenuItem(4, menuItemAddDTO.getName(), menuItemAddDTO.getIngredients());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuItem);
    }
}
