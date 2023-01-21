package com.exercicios.jogobixo.core.usecase;

import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaResultadoUseCaseTest {
    @Mock
    ResultadoRepository resultadoRepositoryMock = Mockito.mock(ResultadoRepository.class);
    @InjectMocks
    ConsultaResultadoUseCase consultaResultado = new ConsultaResultadoUseCase();

    @Test
    public void deveConsultarResultadoPorDiaQuandoExistirMaisDeUmResultadoDisponivel() {
        LocalDate disponivelEm = LocalDate.of(2022, Month.JANUARY, 30);

        Mockito.when(resultadoRepositoryMock.consultarPorData(disponivelEm))
                .thenReturn(Optional.of(ResultadoDiaMock.mockResultadoDiaCompleto(disponivelEm)));

        Optional<ResultadoDia> resultado = consultaResultado.consultarPorData(disponivelEm);

        assertTrue(resultado.isPresent());
        assertEquals(resultado.get().getHorarios().size(), 5);
    }

    @Test
    public void deveConsutarResultadoPorDiaNaoRetornarResultados() {
        LocalDate disponivelEm = LocalDate.of(2022, Month.JANUARY, 30);

        Mockito.when(resultadoRepositoryMock.consultarPorData(disponivelEm))
                .thenReturn(Optional.empty());

        Optional<ResultadoDia> resultado = consultaResultado.consultarPorData(disponivelEm);

        assertFalse(resultado.isPresent());
    }

}