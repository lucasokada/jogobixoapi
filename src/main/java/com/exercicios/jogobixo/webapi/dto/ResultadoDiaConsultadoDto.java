package com.exercicios.jogobixo.webapi.dto;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoHorario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record ResultadoDiaConsultadoDto(
        String sorteadoEm,
        List<ResultadoHorarioDto> horarios

) implements ResultadoDiaDto {
    public ResultadoDiaConsultadoDto(String sorteadoEm, List<ResultadoHorarioDto> horarios) {
        this.sorteadoEm = sorteadoEm;
        this.horarios = horarios;
    }

    public ResultadoDiaConsultadoDto(ResultadoDia dominio) {
        this(
                formatarDataAnoMesDia(dominio.getSorteadoEm()), extraiDe(dominio)
        );
    }

    private static String formatarDataAnoMesDia(LocalDate sorteadoEm) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return sorteadoEm.format(formatter);
    }

    private static List<ResultadoHorarioDto> extraiDe(ResultadoDia dominio) {
        List<ResultadoHorarioDto> resultados = new ArrayList<>(dominio.getHorarios().keySet().stream().map(horarioJogo ->
                new ResultadoHorarioDto(horarioJogo, dominio.getHorarios().get(horarioJogo).stream()
                        .map(ResultadoHorario::resultado).toList())).toList());

        resultados.sort(new Comparator<ResultadoHorarioDto>() {
            @Override
            public int compare(ResultadoHorarioDto o1, ResultadoHorarioDto o2) {
                return o1.horario().getOrdem() - o2.horario().getOrdem();
            }
        });

        return resultados;
    }
}
