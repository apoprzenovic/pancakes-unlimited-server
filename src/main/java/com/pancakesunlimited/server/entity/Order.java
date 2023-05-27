package com.pancakesunlimited.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @ManyToOne
    @JoinColumn(name = "pancake_id")
    private Pancake pancake;

    @Column(name = "price")
    private BigDecimal price;

}
