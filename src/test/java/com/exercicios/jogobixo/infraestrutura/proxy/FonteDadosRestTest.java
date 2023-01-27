package com.exercicios.jogobixo.infraestrutura.proxy;

import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import org.jsoup.helper.DataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class FonteDadosRestTest {
    @Mock
    HttpRestProxy restProxyMock = Mockito.mock(HttpRestProxy.class);
    @InjectMocks
    FonteDadoRest fonteDado = new FonteDadoRest();

    @Test
    public void deveConsultarPorHorariosERetornarResultadoValido() throws IOException {
        Mockito
                .when(restProxyMock.getHtmlFromUrl(Mockito.any()))
                .thenReturn(DataUtil.load(recuperarHtmlMock(), "UTF-8", "http://test.com"));

        var resultadoConsulta = fonteDado.recuperarPorHorario(Set.of(HorarioJogos.PT, HorarioJogos.COR));

        assertEquals(2, resultadoConsulta.size());

        //assercoes para o resultado PT
        var resultadoPT = resultadoConsulta.stream().filter(x -> x.horario() == HorarioJogos.PT).findFirst();
        assertTrue(resultadoPT.isPresent());
        assertEquals(7, resultadoPT.get().resultados().size());
        assertEquals(List.of("7172-18", "4524-6", "0723-6", "5808-2", "1644-11", "9871-18", "446-12"),
                resultadoPT.get().resultados());

        //assercoes para o resultado PT
        var resultadoCOR = resultadoConsulta.stream().filter(x -> x.horario() == HorarioJogos.COR).findFirst();
        assertTrue(resultadoCOR.isPresent());
        assertEquals(7, resultadoCOR.get().resultados().size());
        assertEquals(List.of("0947-12", "8273-19", "1176-19", "5896-24", "8905-2", "5197-25", "834-9"),
                resultadoCOR.get().resultados());
    }

    @Test
    public void deveConsultarPorHorarioENaoObterRetorno() throws IOException {
        Mockito
                .when(restProxyMock.getHtmlFromUrl(Mockito.any()))
                .thenReturn(DataUtil.load(recuperarHtmlIncompetoResultadoMock(), "UTF-8", "http://test.com"));

        var resultadoConsulta = fonteDado.recuperarPorHorario(Set.of(HorarioJogos.PTM));

        assertEquals(0, resultadoConsulta.size());
    }

    private static File recuperarHtmlMock() {
        ClassLoader classLoader = FonteDadosRestTest.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource("mockResultadoFonte.html")).getFile());
    }

    private static File recuperarHtmlIncompetoResultadoMock() {
        ClassLoader classLoader = FonteDadosRestTest.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource("mockResultadoFonteIncompleto.html")).getFile());
    }
}
