package com.exercicios.jogobixo.core.dominio.dto;

import com.exercicios.jogobixo.core.dominio.HorarioJogos;

import java.util.List;

public record ConsultaResultadoDto (
        HorarioJogos horario,
        List<String> resultados
) {}
