package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.config.AutorizacaoProperties;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.example.validation.SaldoValidator;
import org.example.validation.TransferenciaValidator;
import org.example.validation.UsuarioValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;
    private final TransferenciaValidator transferenciaValidator;
    private final SaldoValidator saldoValidator;
    private final RestTemplate restTemplate;
    private final AutorizacaoProperties autorizacaoProperties;


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

    public boolean verificarAutorizacao() {
        try {
            ResponseEntity<AutorizacaoResponse> response = restTemplate.getForEntity(autorizacaoProperties.getUrl(), AutorizacaoResponse.class);
            return response.getBody() != null && response.getBody().getData().isAuthorization();
        } catch (Exception e) {
            return false;
        }
    }

    private static class AutorizacaoResponse {
        private String status;
        private Data data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public static class Data {
            private boolean authorization;

            public boolean isAuthorization() {
                return authorization;
            }

            public void setAuthorization(boolean authorization) {
                this.authorization = authorization;
            }
        }
    }
}

