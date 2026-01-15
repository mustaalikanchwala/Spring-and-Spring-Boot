package com.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserSpringCRUDJPA user;

    @ManyToMany(mappedBy = "post")
    private List<Tags> tags;
}
