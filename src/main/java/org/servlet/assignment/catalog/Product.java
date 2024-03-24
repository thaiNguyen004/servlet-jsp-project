package org.servlet.assignment.catalog;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name of the product must not be null")
    private String name;

    @NotNull(message = "The size name of the product must not be null")
    private String size;

    @VietNamDong(fieldName = "price", min = 1000, message = "The price must not be null")
    private String price;

    @IntRange(message = "The quantity must not be null", min = 1, max = 1000, fieldName = "quantity")
    private String quantity;

    @ManyToOne
    private Category category;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

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
