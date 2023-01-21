package com.exercicios.jogobixo.core.dominio;

import java.time.LocalDate;
import java.util.Optional;

public interface ResultadoRepository {
    Optional<ResultadoDia> consultarPorData(LocalDate filtro);
}
