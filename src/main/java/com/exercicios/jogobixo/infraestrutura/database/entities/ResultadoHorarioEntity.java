package com.exercicios.jogobixo.infraestrutura.database.entities;

import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "[resultado_horario]")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultadoHorarioEntity that = (ResultadoHorarioEntity) o;
        return ordem == that.ordem && Objects.equals(id, that.id) && Objects.equals(resultado, that.resultado) && codigoHorario == that.codigoHorario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ordem, resultado, codigoHorario);
    }
}
