package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String senha;
    private BigDecimal saldo;
}
