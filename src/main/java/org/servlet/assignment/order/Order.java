package org.servlet.assignment.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.servlet.assignment.user.Address;
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
    private User user;
    @ManyToOne
    private Address address;

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

