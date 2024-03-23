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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column
    private String message;

    @Column(name = "DT_INSERT", nullable = false)
    private Date dtInsert;

    @Column(name = "DT_UPDATE")
    private Date dtUpdate;
}
