package com.blog.coffee_shop.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuItemAddDTO {

    private String name;

    private List<String> ingredients = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "MenuItemAddDTO{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
