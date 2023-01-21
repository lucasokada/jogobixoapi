package com.exercicios.jogobixo.core.usecase;

import com.exercicios.jogobixo.core.dominio.FonteResultadoRepository;
import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.dominio.ResultadoRepository;
import com.exercicios.jogobixo.core.dominio.dto.ConsultaResultadoDto;
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
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ImportarResultadoUseCaseTest {
    @Mock
    ResultadoRepository resultadoRepositoryMock = Mockito.mock(ResultadoRepository.class);
    @Mock
    FonteResultadoRepository fonteResultadoRepository = Mockito.mock(FonteResultadoRepository.class);
    @InjectMocks
    ImportarResultadoUseCase importarResultadoUseCase = new ImportarResultadoUseCase();

    @Test
    public void deveImportarResultadoQuandoExistirNaFonteEComDadoExistenteNoBanco() {
        Mockito.when(resultadoRepositoryMock.consultarPorData(Mockito.any()))
                .thenReturn(Optional.of(new ResultadoDia(LocalDate.now())));

        Mockito.when(fonteResultadoRepository.recuperarPorHorario(
                Set.of(HorarioJogos.PTM, HorarioJogos.PT, HorarioJogos.PTV, HorarioJogos.COR, HorarioJogos.FED)
        )).thenReturn(Arrays.asList(
            new ConsultaResultadoDto(HorarioJogos.PTM, Arrays.asList("4222-6", "3556-14")),
            new ConsultaResultadoDto(HorarioJogos.PT, Arrays.asList("4222-6", "3556-14")),
            new ConsultaResultadoDto(HorarioJogos.PTV, Arrays.asList("4222-6", "3556-14")))
        );

        importarResultadoUseCase.importar();

        Mockito.verify(resultadoRepositoryMock, Mockito.times(1))
            .salvar(Mockito.any(ResultadoDia.class));
    }

    @Test
    public void deveImportarResultadoQuandoExistirNaFonteEComDadoInexistenteNoBanco() {
        Mockito.when(resultadoRepositoryMock.consultarPorData(Mockito.any()))
                .thenReturn(Optional.empty());

        Mockito.when(fonteResultadoRepository.recuperarPorHorario(
                Set.of(HorarioJogos.PTM, HorarioJogos.PT, HorarioJogos.PTV, HorarioJogos.COR, HorarioJogos.FED)
        )).thenReturn(Arrays.asList(
                new ConsultaResultadoDto(HorarioJogos.PTM, Arrays.asList("4222-6", "3556-14")),
                new ConsultaResultadoDto(HorarioJogos.PT, Arrays.asList("4222-6", "3556-14")),
                new ConsultaResultadoDto(HorarioJogos.PTV, Arrays.asList("4222-6", "3556-14")))
        );

        importarResultadoUseCase.importar();

        Mockito.verify(resultadoRepositoryMock, Mockito.times(1))
                .salvar(Mockito.any(ResultadoDia.class));
    }

    @Test
    public void naoDeveImportarQuandoResultadosEstiveremCompletos() {
        Mockito.when(resultadoRepositoryMock.consultarPorData(Mockito.any()))
                .thenReturn(Optional.of(ResultadoDiaMock.mockResultadoDiaCompleto(LocalDate.now())));

        importarResultadoUseCase.importar();

        Mockito.verify(resultadoRepositoryMock, Mockito.times(0))
                .salvar(Mockito.any(ResultadoDia.class));
    }
}
