package org.example.validation;

import org.example.exception.SaldoInsuficienteException;
import org.example.exception.UsuarioNaoEncontradoException;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UsuarioValidator {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario validarUsuarioExistente(Long id) throws UsuarioNaoEncontradoException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado."));
    }

    public void validarSaldo(Usuario usuario, BigDecimal valor) {
        if (usuario.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência.");
        }
    }


}

