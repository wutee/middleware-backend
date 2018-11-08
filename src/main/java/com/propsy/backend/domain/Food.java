package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Food.
 */
@Entity
@Table(name = "food")
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_slug", nullable = false)
    private String nameSlug;

    @Column(name = "food_description")
    private String foodDescription;

    @Column(name = "calories")
    private Integer calories;

    @Column(name = "is_spicy")
    private Boolean isSpicy;

    @Column(name = "is_vegetarian")
    private Boolean isVegetarian;

    @Column(name = "is_gluten_free")
    private Boolean isGlutenFree;

    @Column(name = "photo_location")
    private String photoLocation;

    @ManyToOne
    @JsonIgnoreProperties("foodLists")
    private FoodInOrder order;

    @ManyToOne
    @JsonIgnoreProperties("foodLists")
    private FoodInMenu menuList;

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

    public Food nameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
        return this;
    }

    public void setNameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public Food foodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
        return this;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public Integer getCalories() {
        return calories;
    }

    public Food calories(Integer calories) {
        this.calories = calories;
        return this;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Boolean isIsSpicy() {
        return isSpicy;
    }

    public Food isSpicy(Boolean isSpicy) {
        this.isSpicy = isSpicy;
        return this;
    }

    public void setIsSpicy(Boolean isSpicy) {
        this.isSpicy = isSpicy;
    }

    public Boolean isIsVegetarian() {
        return isVegetarian;
    }

    public Food isVegetarian(Boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
        return this;
    }

    public void setIsVegetarian(Boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
    }

    public Boolean isIsGlutenFree() {
        return isGlutenFree;
    }

    public Food isGlutenFree(Boolean isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
        return this;
    }

    public void setIsGlutenFree(Boolean isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public Food photoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
        return this;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public FoodInOrder getOrder() {
        return order;
    }

    public Food order(FoodInOrder foodInOrder) {
        this.order = foodInOrder;
        return this;
    }

    public void setOrder(FoodInOrder foodInOrder) {
        this.order = foodInOrder;
    }

    public FoodInMenu getMenuList() {
        return menuList;
    }

    public Food menuList(FoodInMenu foodInMenu) {
        this.menuList = foodInMenu;
        return this;
    }

    public void setMenuList(FoodInMenu foodInMenu) {
        this.menuList = foodInMenu;
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
        Food food = (Food) o;
        if (food.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), food.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Food{" +
            "id=" + getId() +
            ", nameSlug='" + getNameSlug() + "'" +
            ", foodDescription='" + getFoodDescription() + "'" +
            ", calories=" + getCalories() +
            ", isSpicy='" + isIsSpicy() + "'" +
            ", isVegetarian='" + isIsVegetarian() + "'" +
            ", isGlutenFree='" + isIsGlutenFree() + "'" +
            ", photoLocation='" + getPhotoLocation() + "'" +
            "}";
    }
}
