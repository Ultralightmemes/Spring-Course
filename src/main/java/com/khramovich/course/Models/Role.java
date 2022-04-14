package com.khramovich.course.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<Cook> cooks;
}
