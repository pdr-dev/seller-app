package com.grupo.casas.bahia.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_filial")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "cnpj")
    private String document;

    @Column(name = "cidade")
    private String city;

    @Column(name = "estado")
    private String state;

    @Column(name = "tipo")
    private String type;

    @Column(name = "atvo")
    private boolean active;

    @Column(name = "dt_registro")
    private LocalDate registrationDate;

    @Column(name = "dh_atualizacao")
    private LocalDateTime lastUpdate;
}