package com.exercicios.jogobixo.webapi;

import com.exercicios.jogobixo.core.usecase.ConsultaResultadoUseCase;
import com.exercicios.jogobixo.core.usecase.ImportarResultadoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resultado-dia")
public class ResultadoDiaController {
    @Autowired
    ImportarResultadoUseCase importarResultado;

    @Autowired
    ConsultaResultadoUseCase consultaResultado;

    @PostMapping
    public ResponseEntity<String> importar() {
        return ResponseEntity.ok("Hello World");
    }

}
