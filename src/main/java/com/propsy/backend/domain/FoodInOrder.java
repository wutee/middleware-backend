package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FoodInOrder.
 */
@Entity
@Table(name = "food_in_order")
public class FoodInOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("foodLists")
    private CustomerOrder orderList;

    @OneToMany(mappedBy = "order")
    private Set<Food> foodLists = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerOrder getOrderList() {
        return orderList;
    }

    public FoodInOrder orderList(CustomerOrder customerOrder) {
        this.orderList = customerOrder;
        return this;
    }

    public void setOrderList(CustomerOrder customerOrder) {
        this.orderList = customerOrder;
    }

    public Set<Food> getFoodLists() {
        return foodLists;
    }

    public FoodInOrder foodLists(Set<Food> foods) {
        this.foodLists = foods;
        return this;
    }

    public FoodInOrder addFoodList(Food food) {
        this.foodLists.add(food);
        food.setOrder(this);
        return this;
    }

    public FoodInOrder removeFoodList(Food food) {
        this.foodLists.remove(food);
        food.setOrder(null);
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
        FoodInOrder foodInOrder = (FoodInOrder) o;
        if (foodInOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foodInOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoodInOrder{" +
            "id=" + getId() +
            "}";
    }
}
