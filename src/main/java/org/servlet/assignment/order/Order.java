package org.servlet.assignment.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.servlet.assignment.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @Column(name = "line_items")
    private List<LineItem> lineItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    @Column(name = "total_price")
    private Integer totalPrice;
    @ManyToOne
    private Discount discount;
    @Column(name = "end_price")
    private Integer endPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private User user;
    @NotBlank(message = "Address is required")
    private String address;
    private String description;
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

