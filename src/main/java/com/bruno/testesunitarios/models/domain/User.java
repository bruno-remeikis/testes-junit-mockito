package com.bruno.testesunitarios.models.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "DT_INSERT", insertable = false, nullable = false) //, nullable = false
    private Date dtInsert;

    @Column(name = "DT_UPDATE", insertable = false)
    private Date dtUpdate;

    public User(Integer id) {
        this.id = id;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
