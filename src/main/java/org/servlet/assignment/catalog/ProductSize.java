package org.servlet.assignment.catalog;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String sizeName;
    @NotNull(message = "The weight of size must not be null")
    private Integer weight;
    @NotNull(message = "The height of size must not be null")
    private Integer height;
    @OneToMany(mappedBy = "size", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
