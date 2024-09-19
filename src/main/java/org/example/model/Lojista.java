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
@DiscriminatorValue("LOJISTA")
public class Lojista extends Usuario {

    @OneToMany(mappedBy = "destinatario")
    private Set<Transferencia> transferenciasComoDestinatario;
}
