package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @PostMapping("/realizar")
    public ResponseEntity<String> realizarTransferencia(
            @RequestParam String remetenteCpf,
            @RequestParam String destinatarioCpf,
            @RequestParam BigDecimal valor) {

        boolean autorizado = transferenciaService.verificarAutorizacao();
        if (autorizado) {
            transferenciaService.realizarTransferencia(remetenteCpf, destinatarioCpf, valor);
            return ResponseEntity.ok("Transferência realizada com sucesso!");
        } else {
            return ResponseEntity.status(400).body("Transferência não autorizada.");
        }
    }

    @GetMapping("/autorizacao")
    public ResponseEntity<String> verificarAutorizacao() {
        boolean autorizado = transferenciaService.verificarAutorizacao();
        return ResponseEntity.ok("Autorizado");
    }

}