package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FoodInMenu.
 */
@Entity
@Table(name = "food_in_menu")
public class FoodInMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @ManyToOne
    @JsonIgnoreProperties("foodLists")
    private Menu menu;

    @OneToMany(mappedBy = "menuList")
    private Set<Food> foodLists = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public FoodInMenu price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public FoodInMenu menu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Set<Food> getFoodLists() {
        return foodLists;
    }

    public FoodInMenu foodLists(Set<Food> foods) {
        this.foodLists = foods;
        return this;
    }

    public FoodInMenu addFoodList(Food food) {
        this.foodLists.add(food);
        food.setMenuList(this);
        return this;
    }

    public FoodInMenu removeFoodList(Food food) {
        this.foodLists.remove(food);
        food.setMenuList(null);
        return this;
    }

    public void setFoodLists(Set<Food> foods) {
        this.foodLists = foods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodInMenu foodInMenu = (FoodInMenu) o;
        if (foodInMenu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foodInMenu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoodInMenu{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
