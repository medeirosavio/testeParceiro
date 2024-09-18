package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Lojista;
import org.example.model.Transferencia;
import org.example.model.Usuario;
import org.example.repository.TransferenciaRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final UsuarioRepository usuarioRepository;

    public Transferencia realizarTransferencia(Usuario remetente, Usuario destinatario, BigDecimal valor) {
        // Validar que o remetente não é um lojista
        if (remetente instanceof Lojista) {
            throw new IllegalArgumentException("Lojistas não podem realizar transferências.");
        }

        // Validar se o remetente tem saldo suficiente
        if (remetente.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência.");
        }

        // Deduzir o saldo do remetente
        remetente.setSaldo(remetente.getSaldo().subtract(valor));

        // Adicionar o valor ao saldo do destinatário
        destinatario.setSaldo(destinatario.getSaldo().add(valor));

        // Persistir as mudanças nos usuários
        usuarioRepository.save(remetente);
        usuarioRepository.save(destinatario);

        // Criar a transferência
        Transferencia transferencia = new Transferencia();
        transferencia.setRemetente(remetente);
        transferencia.setDestinatario(destinatario);
        transferencia.setValor(valor);
        transferencia.setDataHoraTransacao(LocalDateTime.now());

        // Salvar a transferência
        return transferenciaRepository.save(transferencia);
    }
}

