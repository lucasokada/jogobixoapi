package com.exercicios.jogobixo.core.usecase;

import com.exercicios.jogobixo.core.dominio.FonteResultadoRepository;
import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoHorario;
import com.exercicios.jogobixo.core.dominio.ResultadoRepository;
import com.exercicios.jogobixo.core.dominio.dto.ConsultaResultadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ImportarResultadoUseCase {
    @Autowired
    ResultadoRepository resultadoRepository;

    @Autowired
    FonteResultadoRepository fonteResultadoRepository;

    public void importar() {
        ResultadoDia resultadoDia = resultadoRepository.consultarPorData(LocalDate.now())
            .orElse(new ResultadoDia(LocalDate.now()));

        if (!resultadoDia.recuperarHorariosPendentes().isEmpty()) {
            List<ConsultaResultadoDto> resultadoDto = fonteResultadoRepository
                    .recuperarPorHorario(resultadoDia.recuperarHorariosPendentes());

            resultadoDto.forEach(resultado -> resultadoDia
                    .inserirResultadoHorario(resultado.horario(), resultado.resultados()));

            resultadoRepository.salvar(resultadoDia);
        }
    }
}
