package com.khramovich.course.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "cooks")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long cook_id;
    private String username;
    private String password;
    @Transient
    transient private String confirmPassword;
    @ManyToMany
    @JoinTable(name = "cook_roles", joinColumns = @JoinColumn(name = "cook_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    public String name;
    @OneToMany
    @JoinTable(name = "cook_dish", joinColumns = @JoinColumn(name = "cook_id", referencedColumnName = "cook_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "dish_id"))
    private Set<Dish> dishes;
    public String surname;
    public String position;
    public String education;
    public Date birthday;
    @Lob
    public byte[] image;
}
