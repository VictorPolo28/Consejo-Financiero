package com.example.Consejo_financiero.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue
    @Column(name = "id_user")
    private Long userId;

    @Column(name ="name", nullable = false,length = 100)
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="email",nullable = false,unique = true)
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="age", nullable = false)
    private int age;

    @Column(name = "balance minimo")
    private Double balanceMinimoAlerta = 1000.0; // Valor por defecto


    @Column(name="singDate", updatable = false)
    @CreationTimestamp
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private UsersStatus userStatus;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Categories> categorias;

}
