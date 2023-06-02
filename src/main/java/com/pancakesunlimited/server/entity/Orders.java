package com.pancakesunlimited.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "order_time", updatable = false)
    private LocalDateTime orderTime;

    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("orders-ordersPancakes")
    private List<OrdersPancake> ordersPancakes;

    @Column(name = "price")
    private BigDecimal price;

}
