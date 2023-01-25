package com.exercicios.jogobixo.infraestrutura.database.entities;

import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resultado_horario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultadoHorarioEntity {
    @Id
    private String id;
    private int ordem;
    private String resultado;

    @Enumerated(EnumType.STRING)
    private HorarioJogos codigoHorario;

    ResultadoHorarioEntity(int ordem, String resultado, HorarioJogos resultadoHorario) {
        this.ordem = ordem;
        this.resultado = resultado;
        this.codigoHorario = resultadoHorario;
        this.id = this.codigoHorario.toString() + this.ordem;
    }
}
