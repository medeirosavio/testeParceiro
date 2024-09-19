package org.example.validation;

import org.example.exception.UsuarioInvalidoException;
import org.example.model.Comum;
import org.example.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaValidator {

    public void validarRemetente(Usuario remetente) {
        if (!(remetente instanceof Comum)) {
            throw new UsuarioInvalidoException("Apenas usuários comuns podem realizar transferências.");
        }
    }
}

