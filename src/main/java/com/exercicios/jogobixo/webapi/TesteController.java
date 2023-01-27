package com.exercicios.jogobixo.webapi;

import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoRepository;
import com.exercicios.jogobixo.infraestrutura.proxy.FonteDadoRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping(value = "/teste")
public class TesteController {
    @Autowired
    ResultadoRepository resultadoRepository;

    public ResultadoDia mockResultadoDiaCompleto(LocalDate disponivelEm) {
        ResultadoDia resultadoDiaMock = new ResultadoDia(disponivelEm);

        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.PTM, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.PT, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.PTV, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.FED, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.COR, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));

        return resultadoDiaMock;
    }

    @GetMapping
    @Transactional
    public ResponseEntity<Optional<ResultadoDia>> hello() {
        FonteDadoRest fonteDadoRest = new FonteDadoRest();
        String diaJogo = fonteDadoRest.recuperaPrimeiroElementoDaTag("p");
        System.out.println(diaJogo);
        String resultadoPTM = fonteDadoRest.recuperaPrimeiroElementoDaTag("th#ptm");
        System.out.println(resultadoPTM);
        String resultados = fonteDadoRest.recuperaPorTagHtml("tbody tr td");
        System.out.println(resultados);
        resultadoRepository.salvar(mockResultadoDiaCompleto(LocalDate.now()));
        return ResponseEntity.ok(resultadoRepository.consultarPorData(LocalDate.now()));
    }
}















