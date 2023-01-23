package com.exercicios.jogobixo.mock;

import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import java.time.LocalDate;
import java.util.Arrays;

public class ResultadoDiaMock {
    public static ResultadoDia mockResultadoDiaCompleto(LocalDate disponivelEm) {
        ResultadoDia resultadoDiaMock = new ResultadoDia(disponivelEm);

        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.PTM, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.PT, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.PTV, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.FED, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.COR, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));

        return resultadoDiaMock;
    }
}