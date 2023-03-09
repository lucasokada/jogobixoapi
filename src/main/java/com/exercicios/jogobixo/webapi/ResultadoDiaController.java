package com.exercicios.jogobixo.webapi;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.webapi.dto.ResultadoConsultaSucessoDto;
import com.exercicios.jogobixo.webapi.dto.ResultadoImportacao;
import com.exercicios.jogobixo.webapi.dto.ResultadoImportacaoFalhoDto;
import com.exercicios.jogobixo.webapi.dto.ResultadoImportacaoDto;
import com.exercicios.jogobixo.core.usecase.ConsultaResultadoUseCase;
import com.exercicios.jogobixo.core.usecase.ImportarResultadoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/resultado-dia")
public class ResultadoDiaController {
    @Autowired
    ImportarResultadoUseCase importarResultado;

    @Autowired
    ConsultaResultadoUseCase consultaResultado;

    @PostMapping
    public ResponseEntity<ResultadoImportacao> importar() {
        try {
            ResultadoDia resultadoDiaImportado = importarResultado.importar();
            var resultadoImportacao = new ResultadoImportacaoDto(resultadoDiaImportado);

            return ResponseEntity.ok(resultadoImportacao);
        } catch (Exception e) {
            var resultadoFalho = new ResultadoImportacaoFalhoDto("Erro de Teste!");

            return ResponseEntity.internalServerError().body(resultadoFalho);
        }
    }

    @GetMapping
    public ResponseEntity<ResultadoConsultaSucessoDto> consultar(@RequestParam(name = "dataJogo", required = true) String dataJogo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataResultado = LocalDate.parse(dataJogo, formatter);
        Optional<ResultadoDia> resultadoDiaConsultado = consultaResultado.consultarPorData(dataResultado);
        if(resultadoDiaConsultado.isPresent()) {
            ResultadoConsultaSucessoDto resultado = new ResultadoConsultaSucessoDto(resultadoDiaConsultado.get());
            return ResponseEntity.ok(resultado);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
