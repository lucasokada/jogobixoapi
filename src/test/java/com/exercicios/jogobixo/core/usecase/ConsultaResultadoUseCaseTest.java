package com.exercicios.jogobixo.core.usecase;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsultaResultadoUseCaseTest {
    @Mock
    ResultadoRepository resultadoRepositoryMock = Mockito.mock(ResultadoRepository.class);
    @InjectMocks
    ConsultaResultadoUseCase consultaResultado = new ConsultaResultadoUseCase();

    @Test
    public void deveConsultarResultadoPorDiaQuandoExistirMaisDeUmResultadoDisponivel() {
        LocalDate disponivelEm = LocalDate.of(2022, Month.JANUARY, 30);

        Mockito.when(resultadoRepositoryMock.consultarPorData(disponivelEm))
                .thenReturn(Optional.of(new ResultadoDia()));

        Optional<ResultadoDia> resultado = consultaResultado.consultarPorData(disponivelEm);

        assertTrue(resultado.isPresent());
        assertEquals(resultado.get().getHorarios().size(), 5);
    }
}
