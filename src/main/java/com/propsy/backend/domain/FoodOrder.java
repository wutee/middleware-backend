package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FoodOrder.
 */
@Entity
@Table(name = "food_order")
public class FoodOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "last_updated_date", nullable = false)
    private LocalDate lastUpdatedDate;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "user_opinion")
    private String userOpinion;

    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "delivery_man_comment")
    private String deliveryManComment;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Restaurant restaurant;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Customer orderee;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private DeliveryPersonnel delivery;

    @ManyToMany
    @JoinTable(name = "food_order_food_items",
               joinColumns = @JoinColumn(name = "food_orders_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "food_items_id", referencedColumnName = "id"))
    private Set<Food> foodItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public FoodOrder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public FoodOrder lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public FoodOrder status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public FoodOrder price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUserOpinion() {
        return userOpinion;
    }

    public FoodOrder userOpinion(String userOpinion) {
        this.userOpinion = userOpinion;
        return this;
    }

    public void setUserOpinion(String userOpinion) {
        this.userOpinion = userOpinion;
    }

    public String getUserComment() {
        return userComment;
    }

    public FoodOrder userComment(String userComment) {
        this.userComment = userComment;
        return this;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getDeliveryManComment() {
        return deliveryManComment;
    }

    public FoodOrder deliveryManComment(String deliveryManComment) {
        this.deliveryManComment = deliveryManComment;
        return this;
    }

    public void setDeliveryManComment(String deliveryManComment) {
        this.deliveryManComment = deliveryManComment;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public FoodOrder loyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
        return this;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public FoodOrder restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Customer getOrderee() {
        return orderee;
    }

    public FoodOrder orderee(Customer customer) {
        this.orderee = customer;
        return this;
    }

    public void setOrderee(Customer customer) {
        this.orderee = customer;
    }

    public DeliveryPersonnel getDelivery() {
        return delivery;
    }

    public FoodOrder delivery(DeliveryPersonnel deliveryPersonnel) {
        this.delivery = deliveryPersonnel;
        return this;
    }

    public void setDelivery(DeliveryPersonnel deliveryPersonnel) {
        this.delivery = deliveryPersonnel;
    }

    public Set<Food> getFoodItems() {
        return foodItems;
    }

    public FoodOrder foodItems(Set<Food> foods) {
        this.foodItems = foods;
        return this;
    }

    public FoodOrder addFoodItems(Food food) {
        this.foodItems.add(food);
        food.getOrders().add(this);
        return this;
    }

    public FoodOrder removeFoodItems(Food food) {
        this.foodItems.remove(food);
        food.getOrders().remove(this);
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
        FoodOrder foodOrder = (FoodOrder) o;
        if (foodOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foodOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", status=" + getStatus() +
            ", price=" + getPrice() +
            ", userOpinion='" + getUserOpinion() + "'" +
            ", userComment='" + getUserComment() + "'" +
            ", deliveryManComment='" + getDeliveryManComment() + "'" +
            ", loyaltyPoints=" + getLoyaltyPoints() +
            "}";
    }
}
