package com.grupo.casas.bahia.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_vendedor")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricula", unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "dt_nascimento")
    private LocalDate birthDate;

    @Column(name = "documento", nullable = false, unique = true)
    private String document;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contrato", nullable = false)
    private ContractType contractType;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}