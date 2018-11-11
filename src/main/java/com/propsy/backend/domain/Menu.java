package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_slug")
    private String nameSlug;

    @ManyToOne
    @JsonIgnoreProperties("menus")
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "menu_food_items",
               joinColumns = @JoinColumn(name = "menus_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "food_items_id", referencedColumnName = "id"))
    private Set<Food> foodItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSlug() {
        return nameSlug;
    }

    public Menu nameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
        return this;
    }

    public void setNameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Menu restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Set<Food> getFoodItems() {
        return foodItems;
    }

    public Menu foodItems(Set<Food> foods) {
        this.foodItems = foods;
        return this;
    }

    public Menu addFoodItems(Food food) {
        this.foodItems.add(food);
        food.getMenus().add(this);
        return this;
    }

    public Menu removeFoodItems(Food food) {
        this.foodItems.remove(food);
        food.getMenus().remove(this);
        return this;
    }

    public void setFoodItems(Set<Food> foods) {
        this.foodItems = foods;
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
        Menu menu = (Menu) o;
        if (menu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", nameSlug='" + getNameSlug() + "'" +
            "}";
    }
}
