package com.exercicios.jogobixo.webapi;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.usecase.ConsultaResultadoUseCase;
import com.exercicios.jogobixo.core.usecase.ImportarResultadoUseCase;
import com.exercicios.jogobixo.webapi.dto.ResultadoDiaConsultadoDto;
import com.exercicios.jogobixo.webapi.dto.ResultadoDiaDto;
import com.exercicios.jogobixo.webapi.dto.ResultadoDiaFalhoDto;
import com.exercicios.jogobixo.webapi.dto.ResultadoDiaSucessoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/resultado-dia")
public class ResultadoDiaController {

    @Autowired
    ImportarResultadoUseCase importarResultado;

    @Autowired
    ConsultaResultadoUseCase consultaResultado;

    @PostMapping
    @Scheduled(cron = "0 50 11 * * *")
    @Scheduled(cron = "0 10 15 * * *")
    @Scheduled(cron = "0 50 16 * * *")
    @Scheduled(cron = "0 10 22 * * *")
    @Scheduled(cron = "0 0 23 * * *")
    public ResponseEntity<ResultadoDiaDto> importar() {
        System.out.println("CHAMOU!!");
        try {
            ResultadoDia resultadoDiaImportado = importarResultado.importar();
            var resultadoImportacao = new ResultadoDiaSucessoDto(resultadoDiaImportado);

            return ResponseEntity.ok(resultadoImportacao);
        } catch (Exception e) {
            var resultadoFalho = new ResultadoDiaFalhoDto("Erro de Teste!");
            return ResponseEntity.internalServerError().body(resultadoFalho);
        }
    }

    @GetMapping
    public ResponseEntity<ResultadoDiaDto> consultar(@RequestParam(name = "dataJogo", required = true) LocalDate resultadoEm) {
        Optional<ResultadoDia> resultadoDiaConsultado = consultaResultado.consultarPorData(resultadoEm);
        if(resultadoDiaConsultado.isPresent()) {
            ResultadoDiaConsultadoDto resultado = new ResultadoDiaConsultadoDto(resultadoDiaConsultado.get());
            return ResponseEntity.ok(resultado);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
