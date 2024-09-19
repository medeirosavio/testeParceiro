package org.example.validation;

import org.example.exception.SaldoInsuficienteException;
import org.example.model.Usuario;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SaldoValidator {
    public void validarSaldo(Usuario remetente, BigDecimal valor) throws SaldoInsuficienteException {
        if (remetente.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência.");
        }
    }
}
