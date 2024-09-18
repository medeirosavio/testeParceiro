package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UsuarioDTO;
import org.example.exception.SaldoInsuficienteException;
import org.example.exception.UsuarioInvalidoException;
import org.example.model.Comum;
import org.example.model.Lojista;
import org.example.model.Transferencia;
import org.example.model.Usuario;
import org.example.repository.TransferenciaRepository;
import org.example.repository.UsuarioRepository;
import org.example.validation.SaldoValidator;
import org.example.validation.TransferenciaValidator;
import org.example.validation.UsuarioValidator;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator = new UsuarioValidator();
    private final TransferenciaValidator transferenciaValidator = new TransferenciaValidator();
    private final SaldoValidator saldoValidator = new SaldoValidator();

    public void realizarTransferencia(UsuarioDTO remetenteDTO, UsuarioDTO destinatarioDTO, BigDecimal valor) {
        try {
            Usuario remetente = converterParaEntidade(remetenteDTO, false);
            Usuario destinatario = converterParaEntidade(destinatarioDTO, true);

            transferenciaValidator.validarRemetente(remetente);
            usuarioValidator.validarSaldo(remetente, valor);
            saldoValidator.validarSaldo(remetente,valor);

            remetente.setSaldo(remetente.getSaldo().subtract(valor));
            destinatario.setSaldo(destinatario.getSaldo().add(valor));

            usuarioRepository.save(remetente);
            usuarioRepository.save(destinatario);

            remetente.setSaldo(remetente.getSaldo().subtract(valor));
            destinatario.setSaldo(destinatario.getSaldo().add(valor));

            System.out.println("Transferência realizada com sucesso!");

        } catch (UsuarioInvalidoException | SaldoInsuficienteException e) {
            System.err.println("Erro ao realizar transferência: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
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

