package org.example.validation;

import org.example.exception.SaldoInsuficienteException;
import org.example.model.Usuario;

import java.math.BigDecimal;

public class UsuarioValidator {

    public void validarSaldo(Usuario usuario, BigDecimal valor) {
        if (usuario.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência.");
        }
    }
}

