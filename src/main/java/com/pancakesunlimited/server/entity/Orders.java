package com.pancakesunlimited.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Entity class for the orders table
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrdersPancake> ordersPancakes;

    @Column(name = "price")
    private BigDecimal price;

}
