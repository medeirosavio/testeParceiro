package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.NotificationRequestDTO;
import org.example.service.NotificationService;
import org.example.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/realizar")
    public ResponseEntity<String> realizarTransferencia(
            @RequestParam String remetenteCpf,
            @RequestParam String destinatarioCpf,
            @RequestParam BigDecimal valor) {

        boolean autorizado = transferenciaService.verificarAutorizacao();
        if (autorizado) {
            transferenciaService.realizarTransferencia(remetenteCpf, destinatarioCpf, valor);
            NotificationRequestDTO remetenteNotification = new NotificationRequestDTO(
                    remetenteCpf, "Transferência realizada com sucesso!");
            notificationService.sendNotification(remetenteNotification);
            return ResponseEntity.ok("Transferência realizada com sucesso!");
        } else {
            return ResponseEntity.status(400).body("Transferência não autorizada.");
        }
    }

    @GetMapping("/autorizar")
    public ResponseEntity<String> verificarAutorizacao() {
        boolean autorizado = transferenciaService.verificarAutorizacao();
        return ResponseEntity.ok("Autorizado");
    }


    @PutMapping("/notificar")
    public ResponseEntity<String> enviarNotificacao(@RequestBody NotificationRequestDTO notificationRequest) {
        boolean sucesso = notificationService.sendNotification(notificationRequest);

        if (sucesso) {
            return ResponseEntity.ok("Notificação enviada com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Falha ao enviar notificação.");
        }
    }

}