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

    private static ResultadoDia mockResultadoDiaCompleto(LocalDate disponivelEm) {
        ResultadoDia resultadoDiaMock = new ResultadoDia(disponivelEm);

        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.PTM, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.PT, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.PTV, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.FED, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));
        resultadoDiaMock.inserirResultadoHorario(HorarioJogos.COR, Arrays.asList("4222-6", "3556-14", "7936-9", "1693-24", "1946-12", "9353-14", "013-4"));

        return resultadoDiaMock;
    }

    @Test
    public void deveConsultarResultadoPorDiaQuandoExistirMaisDeUmResultadoDisponivel() {
        LocalDate disponivelEm = LocalDate.of(2022, Month.JANUARY, 30);

        Mockito.when(resultadoRepositoryMock.consultarPorData(disponivelEm))
                .thenReturn(Optional.of(mockResultadoDiaCompleto(disponivelEm)));

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