package com.apuliadigitalmaker.buytopia.categories;


import com.apuliadigitalmaker.buytopia.products.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categories", schema = "Buytopia")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonIgnore
    @ColumnDefault("current_timestamp()")
    @Column(name = "created", updatable = false)
    private Instant created;

    @JsonIgnore
    @ColumnDefault("current_timestamp()")
    @Column(name = "updated")
    private Instant updated;

    @JsonIgnore
    @Column(name = "deleted")
    private Instant deleted;


    @ColumnDefault("current_timestamp()")
    @Column(name = "created_at")
    private Instant createdAt;

    // Modifica la data di creazione
    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Modifica la data di aggiornamento
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public void softDelete() {this.deletedAt = Instant.now();}

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new LinkedHashSet<>();

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setName(String value) {
    }

    public void setDescription(String value) {
    }
}
