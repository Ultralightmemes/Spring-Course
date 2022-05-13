package com.khramovich.course.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "dish_order")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long order_id;
    private Date finish_date;
    @OneToMany
    @JoinTable(name = "order_dish", joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "dish_id"))
    private Set<Dish> dishes;
    @ManyToOne
    @JoinTable(name = "order_user", joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "cook_id", referencedColumnName = "cook_id"))
    private Cook user;
    private String comment;
}
