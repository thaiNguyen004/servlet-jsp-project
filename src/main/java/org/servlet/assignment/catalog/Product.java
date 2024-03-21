package org.servlet.assignment.catalog;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.servlet.assignment.validation.IntRange;
import org.servlet.assignment.validation.VietNamDong;

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

    @NotBlank(message = "The name of the product must not be null")
    private String name;

    @ManyToOne(targetEntity = ProductSize.class)
    @NotNull(message = "The size of the product must not be null")
    private ProductSize size;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    /*@Size(min = 1, message = "The product must have at least 1 picture")*/
    private List<Picture> pictures = new ArrayList<>();

    @VietNamDong(fieldName = "price", min = 1000, message = "The price must not be null")
    private String price;

    @ManyToOne
    @NotNull(message = "The category of the product must not be null")
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
