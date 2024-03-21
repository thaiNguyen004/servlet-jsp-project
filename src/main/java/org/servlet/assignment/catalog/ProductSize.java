package org.servlet.assignment.catalog;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.servlet.assignment.validation.IntRange;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT_SIZES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size_name")
    @NotNull(message = "The name of size must not be null")
    private String sizeName;

    @IntRange(fieldName = "width", min = 45, max = 60)
    private String width;

    @IntRange(fieldName = "length", min = 63, max = 76)
    private String length;

    @OneToMany(mappedBy = "size", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public ProductSize(Long id) {
        this.id = id;
    }
}
