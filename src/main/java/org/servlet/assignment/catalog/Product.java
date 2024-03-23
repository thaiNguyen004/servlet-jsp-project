package org.servlet.assignment.catalog;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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

    @ManyToOne
    private Category category;

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
