package com.exercicios.jogobixo.infraestrutura.database.entities;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "resultado_dia")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultadoDiaEntity {
    @Id
    private LocalDate sorteadoEm;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "resultado_dia_id")
    private List<ResultadoHorarioEntity> horarios = new ArrayList<>();

    public ResultadoDia toDomain() {
        ResultadoDia resultadoDia = new ResultadoDia(this.sorteadoEm);
        this.horarios.forEach(horario -> resultadoDia.inserirResultadoHorario(horario.getCodigoHorario(), horario.getResultado()));
        return resultadoDia;
    }

    public ResultadoDiaEntity(ResultadoDia dominio) {
        this.sorteadoEm = dominio.getSorteadoEm();
        dominio.getHorarios().keySet().forEach(chave -> {
            dominio.getHorarios().get(chave).forEach(resultado -> {
                this.horarios.add(new ResultadoHorarioEntity(resultado.ordem(), resultado.resultado(), chave));
            });
        });
    }
}
