package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Language.
 */
@Entity
@Table(name = "language")
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_slug")
    private String nameSlug;

    @OneToMany(mappedBy = "language")
    private Set<Translation> translations = new HashSet<>();
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

    public Language nameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
        return this;
    }

    public void setNameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
    }

    public Set<Translation> getTranslations() {
        return translations;
    }

    public Language translations(Set<Translation> translations) {
        this.translations = translations;
        return this;
    }

    public Language addTranslation(Translation translation) {
        this.translations.add(translation);
        translation.setLanguage(this);
        return this;
    }

    public Language removeTranslation(Translation translation) {
        this.translations.remove(translation);
        translation.setLanguage(null);
        return this;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
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
        Language language = (Language) o;
        if (language.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), language.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Language{" +
            "id=" + getId() +
            ", nameSlug='" + getNameSlug() + "'" +
            "}";
    }
}
