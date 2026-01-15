package com.example.model;

import jakarta.persistence.*;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id") //Specifies the foreign key column name in the database table
    private UserSpringCRUDJPA user;
}
