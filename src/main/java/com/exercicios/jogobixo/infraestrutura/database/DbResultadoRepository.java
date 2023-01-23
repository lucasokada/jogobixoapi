package com.exercicios.jogobixo.infraestrutura.database;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoRepository;

import java.time.LocalDate;
import java.util.Optional;

public class DbResultadoRepository implements ResultadoRepository {
    @Override
    public Optional<ResultadoDia> consultarPorData(LocalDate filtro) {
        return Optional.empty();
    }

    @Override
    public void salvar(ResultadoDia resultadoDia) {
    }
}
