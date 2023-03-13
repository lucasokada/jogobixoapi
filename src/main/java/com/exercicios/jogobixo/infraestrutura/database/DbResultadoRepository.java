package com.exercicios.jogobixo.infraestrutura.database;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoRepository;
import com.exercicios.jogobixo.infraestrutura.database.entities.ResultadoDiaEntity;
import com.exercicios.jogobixo.infraestrutura.database.entities.ResultadoDiaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DbResultadoRepository implements ResultadoRepository {
    @Autowired
    private ResultadoDiaJpaRepository resultadoDiaJpaRepository;

    @Override
    public Optional<ResultadoDia> consultarPorData(LocalDate filtro) {
        var resultadoDia = this.resultadoDiaJpaRepository.findById(filtro);
        return resultadoDia.map(ResultadoDiaEntity::toDomain);
    }

    @Override
    public void salvar(ResultadoDia resultadoDia) {
        var debugValue = new ResultadoDiaEntity(resultadoDia);
        this.resultadoDiaJpaRepository.save(debugValue);
    }
}
