package org.servlet.assignment.catalog;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(targetEntity = ProductSize.class)
    @NotNull(message = "The size of the product must not be null")
    private ProductSize size;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Picture> pictures = new ArrayList<>();
    private Integer price;
    @ManyToOne
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    @PreUpdate
    private void updateTime() {
        updatedAt = new Date();
    }

    @PrePersist
    private void createTime() {
        updatedAt = new Date();
    }
}
