package com.exercicios.jogobixo.core.dominio;

import com.exercicios.jogobixo.core.dominio.dto.ConsultaResultadoDto;
import java.util.List;
import java.util.Set;

public interface FonteResultadoRepository {
    List<ConsultaResultadoDto> recuperarPorHorario(Set<HorarioJogos> horarios);
}
