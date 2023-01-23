package com.exercicios.jogobixo.infraestrutura.database;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.mock.ResultadoDiaMock;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DbResultadoRepositoryTest {

    DbResultadoRepository DbResultadoRepository;

    @Test
    public void deveSalvarUmResultadoEConsultarPorData() {
        LocalDate dataAtual = LocalDate.now();
        ResultadoDia resultadoMock = ResultadoDiaMock.mockResultadoDiaCompleto(dataAtual);

        DbResultadoRepository.salvar(resultadoMock);
        Optional<ResultadoDia> resultadoPersistido = DbResultadoRepository.consultarPorData(dataAtual);

        assertTrue(resultadoPersistido.isPresent());

        ResultadoDia resultadoValor = resultadoPersistido.get();
        assertEquals(resultadoValor.getSorteadoEm(), resultadoMock.getSorteadoEm());
        assertEquals(resultadoValor.getHorarios(), resultadoMock.getHorarios());
    }

    @Test
    public void deveConsultarUmResultadoInexistente() {
        LocalDate dataFiltro = LocalDate.now().plusDays(1);
        Optional<ResultadoDia> resultadoPersistido = DbResultadoRepository.consultarPorData(dataFiltro);

        assertTrue(resultadoPersistido.isEmpty());
    }
}
