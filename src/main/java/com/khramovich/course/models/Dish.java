package com.khramovich.course.models;

import lombok.*;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
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
    public String name;
    public String Ingredients;
    public int price;
    public String country;
    @ManyToOne
    @JoinTable(name = "cook_dish", joinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "cook_id", referencedColumnName = "cook_id"))
    private Cook cook;
    @Lob
    private byte[] image;
    private String description;
    @ManyToMany
    @JoinTable(name = "dish_dish_set", joinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "set_id", referencedColumnName = "set_id"))
    private Set<Dish_set> dish_sets;
    public String parseImage() throws UnsupportedEncodingException {
        if (image != null) {
            byte[] encodeBase64 = Base64.getEncoder().encode(image);
            return new String(encodeBase64, "UTF-8");
        }else return "";
    }
}
