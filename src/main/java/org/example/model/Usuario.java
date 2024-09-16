package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    protected String nomeCompleto;
    @Column(unique = true, nullable = false)
    protected String cpf;
    @Column(unique = true, nullable = false)
    protected String email;
    @Column(nullable = false)
    protected String senha;
    @Column(precision = 10, scale = 2)
    private double saldo;

}

