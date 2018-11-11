package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ingredient.
 */
@Entity
@Table(name = "ingredient")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_slug", nullable = false)
    private String nameSlug;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "photo_location")
    private String photoLocation;

    @Column(name = "ingredient_description")
    private String ingredientDescription;

    @ManyToMany(mappedBy = "ingredientLists")
    @JsonIgnore
    private Set<IngredientOrder> orders = new HashSet<>();

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

    public Ingredient nameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
        return this;
    }

    public void setNameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
    }

    public Float getPrice() {
        return price;
    }

    public Ingredient price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public Ingredient photoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
        return this;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public String getIngredientDescription() {
        return ingredientDescription;
    }

    public Ingredient ingredientDescription(String ingredientDescription) {
        this.ingredientDescription = ingredientDescription;
        return this;
    }

    public void setIngredientDescription(String ingredientDescription) {
        this.ingredientDescription = ingredientDescription;
    }

    public Set<IngredientOrder> getOrders() {
        return orders;
    }

    public Ingredient orders(Set<IngredientOrder> ingredientOrders) {
        this.orders = ingredientOrders;
        return this;
    }

    public Ingredient addOrder(IngredientOrder ingredientOrder) {
        this.orders.add(ingredientOrder);
        ingredientOrder.getIngredientLists().add(this);
        return this;
    }

    public Ingredient removeOrder(IngredientOrder ingredientOrder) {
        this.orders.remove(ingredientOrder);
        ingredientOrder.getIngredientLists().remove(this);
        return this;
    }

    public void setOrders(Set<IngredientOrder> ingredientOrders) {
        this.orders = ingredientOrders;
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
        Ingredient ingredient = (Ingredient) o;
        if (ingredient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ingredient{" +
            "id=" + getId() +
            ", nameSlug='" + getNameSlug() + "'" +
            ", price=" + getPrice() +
            ", photoLocation='" + getPhotoLocation() + "'" +
            ", ingredientDescription='" + getIngredientDescription() + "'" +
            "}";
    }
}
