package com.exercicios.jogobixo.infraestrutura.proxy;

import com.exercicios.jogobixo.core.dominio.FonteResultadoRepository;
import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.dto.ConsultaResultadoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class FonteDadoRest implements FonteResultadoRepository {

    @Override
    public List<ConsultaResultadoDto> recuperarPorHorario(Set<HorarioJogos> horarios) {
        return null;
    }
}
