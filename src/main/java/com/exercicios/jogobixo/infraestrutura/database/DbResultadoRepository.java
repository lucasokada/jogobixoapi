package com.exercicios.jogobixo.infraestrutura.database;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoRepository;
import com.exercicios.jogobixo.infraestrutura.database.entities.ResultadoDiaEntity;
import com.exercicios.jogobixo.infraestrutura.database.entities.ResultadoDiaJpaRepository;
import com.exercicios.jogobixo.infraestrutura.database.entities.ResultadoHorarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

@Component
public class DbResultadoRepository implements ResultadoRepository {
    @Autowired
    private ResultadoDiaJpaRepository resultadoDiaJpaRepository;

    @Override
    public Optional<ResultadoDia> consultarPorData(LocalDate filtro) {
        var resultadoDia = this.resultadoDiaJpaRepository.findById(filtro);
        resultadoDia.get().getHorarios().sort(new Comparator<ResultadoHorarioEntity>() {
            @Override
            public int compare(ResultadoHorarioEntity o1, ResultadoHorarioEntity o2) {
                return o1.getOrdem() - o2.getOrdem();
            }
        });

        return resultadoDia.map(ResultadoDiaEntity::toDomain);
    }

    @Override
    public void salvar(ResultadoDia resultadoDia) {
        var debugValue = new ResultadoDiaEntity(resultadoDia);
        this.resultadoDiaJpaRepository.save(debugValue);
    }
}
