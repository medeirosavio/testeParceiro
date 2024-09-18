package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UsuarioDTO;
import org.example.service.TransferenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping("/realizar")
    public ResponseEntity<String> realizarTransferencia(
            @Valid @RequestBody UsuarioDTO remetenteDTO,
            @Valid @RequestBody UsuarioDTO destinatarioDTO,
            @RequestParam BigDecimal valor) {

        try {
            transferenciaService.realizarTransferencia(remetenteDTO, destinatarioDTO, valor);
            return ResponseEntity.ok("Transferência realizada com sucesso!");
        } catch (Exception e) {
            // Exceções personalizadas podem ser tratadas de forma mais específica aqui
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao realizar transferência: " + e.getMessage());
        }
    }
}