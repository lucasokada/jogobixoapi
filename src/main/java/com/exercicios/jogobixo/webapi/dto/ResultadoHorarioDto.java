package com.exercicios.jogobixo.webapi.dto;

import java.util.List;

public record ResultadoHorarioDto(
        com.exercicios.jogobixo.core.dominio.HorarioJogos horario,
        List<String> numerosSorteados
) {
}
