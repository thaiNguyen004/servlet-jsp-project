package org.servlet.assignment.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.servlet.assignment.validation.IntRange;
import org.servlet.assignment.validation.VietNamDong;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "DISCOUNTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @VietNamDong(message = "Value must be in VND", fieldName = "value")
    private String value;
    @IntRange(min = 1, message = "Value must be between 1 and 100", fieldName = "quantity")
    private String quantity;
    @NotNull(message = "Start datetime is required")
    private LocalDateTime start;
    @NotNull(message = "End datetime is required")
    private LocalDateTime end;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @PreUpdate
    private void updateTime() {
        updatedAt = new Date();
    }

    @PrePersist
    private void createTime() {
        updatedAt = new Date();
    }
}
