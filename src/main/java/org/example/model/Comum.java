package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("COMUM")
public class Comum extends Usuario {

    @OneToMany(mappedBy = "remetente")
    private Set<Transferencia> transferenciasComoRemetente;

    @OneToMany(mappedBy = "destinatario")
    private Set<Transferencia> transferenciasComoDestinatario;
}
