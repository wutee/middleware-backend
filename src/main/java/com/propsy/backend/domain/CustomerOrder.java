package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CustomerOrder.
 */
@Entity
@Table(name = "customer_order")
public class CustomerOrder implements Serializable {

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

    @OneToMany(mappedBy = "orderList")
    private Set<FoodInOrder> foodLists = new HashSet<>();
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

    public CustomerOrder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public CustomerOrder lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public CustomerOrder status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public CustomerOrder price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUserOpinion() {
        return userOpinion;
    }

    public CustomerOrder userOpinion(String userOpinion) {
        this.userOpinion = userOpinion;
        return this;
    }

    public void setUserOpinion(String userOpinion) {
        this.userOpinion = userOpinion;
    }

    public String getUserComment() {
        return userComment;
    }

    public CustomerOrder userComment(String userComment) {
        this.userComment = userComment;
        return this;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getDeliveryManComment() {
        return deliveryManComment;
    }

    public CustomerOrder deliveryManComment(String deliveryManComment) {
        this.deliveryManComment = deliveryManComment;
        return this;
    }

    public void setDeliveryManComment(String deliveryManComment) {
        this.deliveryManComment = deliveryManComment;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public CustomerOrder loyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
        return this;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Set<FoodInOrder> getFoodLists() {
        return foodLists;
    }

    public CustomerOrder foodLists(Set<FoodInOrder> foodInOrders) {
        this.foodLists = foodInOrders;
        return this;
    }

    public CustomerOrder addFoodList(FoodInOrder foodInOrder) {
        this.foodLists.add(foodInOrder);
        foodInOrder.setOrderList(this);
        return this;
    }

    public CustomerOrder removeFoodList(FoodInOrder foodInOrder) {
        this.foodLists.remove(foodInOrder);
        foodInOrder.setOrderList(null);
        return this;
    }

    public void setFoodLists(Set<FoodInOrder> foodInOrders) {
        this.foodLists = foodInOrders;
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
        CustomerOrder customerOrder = (CustomerOrder) o;
        if (customerOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
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
