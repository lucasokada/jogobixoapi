package com.exercicios.jogobixo.core.usecase;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ConsultaResultadoUseCase {
    @Autowired
    private ResultadoRepository resultadoRepository;

    public Optional<ResultadoDia> consultarPorData(LocalDate filtro) {
        return Optional.of(new ResultadoDia());
    }
}
