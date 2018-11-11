package com.propsy.backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A IngredientOrder.
 */
@Entity
@Table(name = "ingredient_order")
public class IngredientOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @Column(name = "jhi_comment")
    private String comment;

    @ManyToMany
    @JoinTable(name = "ingredient_order_ingredient_list",
               joinColumns = @JoinColumn(name = "ingredient_orders_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ingredient_lists_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredientLists = new HashSet<>();

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

    public IngredientOrder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public IngredientOrder totalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getComment() {
        return comment;
    }

    public IngredientOrder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Ingredient> getIngredientLists() {
        return ingredientLists;
    }

    public IngredientOrder ingredientLists(Set<Ingredient> ingredients) {
        this.ingredientLists = ingredients;
        return this;
    }

    public IngredientOrder addIngredientList(Ingredient ingredient) {
        this.ingredientLists.add(ingredient);
        ingredient.getOrders().add(this);
        return this;
    }

    public IngredientOrder removeIngredientList(Ingredient ingredient) {
        this.ingredientLists.remove(ingredient);
        ingredient.getOrders().remove(this);
        return this;
    }

    public void setIngredientLists(Set<Ingredient> ingredients) {
        this.ingredientLists = ingredients;
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
        IngredientOrder ingredientOrder = (IngredientOrder) o;
        if (ingredientOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientOrder{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
