package com.exercicios.jogobixo.webapi.dto;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public record ResultadoConsultaSucessoDto(
        String sorteadoEm,
        Set<String> horarios
) implements ResultadoConsulta {

    private static String formatarDataAnoMesDia(LocalDate sorteadoEm) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return sorteadoEm.format(formatter);
    }

    public ResultadoConsultaSucessoDto(ResultadoDia resultadoDia) {
        this(formatarDataAnoMesDia(resultadoDia.getSorteadoEm()),
                resultadoDia.getHorarios().keySet().stream().map(Enum::toString).collect(Collectors.toSet()));
    }
}
