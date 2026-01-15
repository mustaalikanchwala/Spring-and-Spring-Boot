package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class UserSpringCRUDJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;
/*
    Simple Rule
    In a bidirectional relationship:

    One side has @JoinColumn → Owning side (creates foreign key)

    Other side has mappedBy → Inverse side (no foreign key)

    mappedBy tells JPA: "Don't create a foreign key column in MY table. The OTHER entity has it

*/

    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Posts> posts;

}
