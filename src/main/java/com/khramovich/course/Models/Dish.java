package com.khramovich.course.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dishes")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long dish_id;
    public String Ingredients;
    public int price;
    public String country;
    @ManyToMany(mappedBy = "dishes")
    private Set<Cook> cooks;
}
