package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UsuarioDTO;
import org.example.exception.SaldoInsuficienteException;
import org.example.exception.UsuarioInvalidoException;
import org.example.model.Comum;
import org.example.model.Lojista;
import org.example.model.Usuario;
import org.example.repository.TransferenciaRepository;
import org.example.repository.UsuarioRepository;
import org.example.validation.SaldoValidator;
import org.example.validation.TransferenciaValidator;
import org.example.validation.UsuarioValidator;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;
    private final TransferenciaValidator transferenciaValidator;
    private final SaldoValidator saldoValidator;

    public String realizarTransferencia(String remetenteCpf, String destinatarioCpf, BigDecimal valor) {
        usuarioValidator.validarUsuarioExistente(remetenteCpf);
        usuarioValidator.validarUsuarioExistente(destinatarioCpf);
        Usuario remetente = usuarioRepository.findByCpf(remetenteCpf).get();
        Usuario destinatario = usuarioRepository.findByCpf(destinatarioCpf).get();
        transferenciaValidator.validarRemetente(remetente);
        usuarioValidator.validarSaldo(remetente, valor);
        saldoValidator.validarSaldo(remetente, valor);
        remetente.setSaldo(remetente.getSaldo().subtract(valor));
        destinatario.setSaldo(destinatario.getSaldo().add(valor));
        usuarioRepository.save(remetente);
        usuarioRepository.save(destinatario);
        remetente.setSaldo(remetente.getSaldo().subtract(valor));
        destinatario.setSaldo(destinatario.getSaldo().add(valor));
        return ("Transação efetuada com sucesso!");
    }

    public Usuario converterParaEntidade(UsuarioDTO usuarioDTO, boolean isLojista) {
        Usuario usuario;

        if (isLojista) {
            usuario = new Lojista();
        } else {
            usuario = new Comum();
        }

        usuario.setNomeCompleto(usuarioDTO.getNomeCompleto());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setSaldo(usuarioDTO.getSaldo());

        return usuario;
    }


}

