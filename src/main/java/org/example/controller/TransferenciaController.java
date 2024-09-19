package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UsuarioDTO;
import org.example.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @PostMapping("/realizar")
    @ResponseStatus(HttpStatus.CREATED)
    public String realizarTransferencia(
            @RequestParam String remetenteCpf,
            @RequestParam String destinatarioCpf,
            @RequestParam BigDecimal valor) {
        return transferenciaService.realizarTransferencia(remetenteCpf,destinatarioCpf,valor);
    }
}