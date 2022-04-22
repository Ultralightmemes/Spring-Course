package com.khramovich.course.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dish_set")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dish_set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long set_id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(name = "dish_dish_set", joinColumns = @JoinColumn(name = "set_id", referencedColumnName = "set_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "dish_id"))
    private Set<Dish> dishes;
}
