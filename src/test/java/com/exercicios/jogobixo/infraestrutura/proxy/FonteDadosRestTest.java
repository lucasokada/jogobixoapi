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
        assertEquals(10, resultadoPT.get().resultados().size());
        assertEquals(List.of("7172-18", "4524-06", "0723-06", "5808-02", "1644-11", "7405-02", "1578-20", "7220-05", "2438-10", "8512-03"),
                resultadoPT.get().resultados());

        //assercoes para o resultado COR
        var resultadoCOR = resultadoConsulta.stream().filter(x -> x.horario() == HorarioJogos.COR).findFirst();
        assertTrue(resultadoCOR.isPresent());
        assertEquals(10, resultadoCOR.get().resultados().size());
        assertEquals(List.of("0947-12", "8273-19", "1176-19", "5896-24", "8905-02", "0815-04", "9218-05", "4779-20", "7366-17", "7375-19"),
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

    @Test
    public void deveConsultarTodosHorariosExcetoUltimo() throws IOException {
        Mockito
                .when(restProxyMock.getHtmlFromUrl(Mockito.any()))
                .thenReturn(DataUtil.load(recuperarHtmlExcetoUltimoResultadoMock(), "UTF-8", "http://test.com"));

        var resultadoConsulta = fonteDado.recuperarPorHorario(Set.of(HorarioJogos.COR));

        assertEquals(0, resultadoConsulta.size());
    }

    @Test
    public void deveManterOrdemResultadosAposConsecutivasConsultas() throws IOException {
        Mockito
                .when(restProxyMock.getHtmlFromUrl(Mockito.any()))
                .thenReturn(DataUtil.load(recuperarHtmlMock(), "UTF-8", "http://test.com"));

        fonteDado.recuperarPorHorario(Set.of(HorarioJogos.PT, HorarioJogos.COR));
        fonteDado.recuperarPorHorario(Set.of(HorarioJogos.PT, HorarioJogos.COR));
        var resultadoConsulta = fonteDado.recuperarPorHorario(Set.of(HorarioJogos.PT, HorarioJogos.COR));

        assertEquals(2, resultadoConsulta.size());

        //assercoes para o resultado PT
        var resultadoPT = resultadoConsulta.stream().filter(x -> x.horario() == HorarioJogos.PT).findFirst();
        assertTrue(resultadoPT.isPresent());
        assertEquals(10, resultadoPT.get().resultados().size());
        assertEquals(List.of("7172-18", "4524-06", "0723-06", "5808-02", "1644-11", "7405-02", "1578-20", "7220-05", "2438-10", "8512-03"),
                resultadoPT.get().resultados());

        //assercoes para o resultado COR
        var resultadoCOR = resultadoConsulta.stream().filter(x -> x.horario() == HorarioJogos.COR).findFirst();
        assertTrue(resultadoCOR.isPresent());
        assertEquals(10, resultadoCOR.get().resultados().size());
        assertEquals(List.of("0947-12", "8273-19", "1176-19", "5896-24", "8905-02", "0815-04", "9218-05", "4779-20", "7366-17", "7375-19"),
                resultadoCOR.get().resultados());
    }

    private static File recuperarHtmlMock() {
        ClassLoader classLoader = FonteDadosRestTest.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource("mockResultadoFonte.html")).getFile());
    }

    private static File recuperarHtmlIncompetoResultadoMock() {
        ClassLoader classLoader = FonteDadosRestTest.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource("mockResultadoFonteIncompleto.html")).getFile());
    }

    private File recuperarHtmlExcetoUltimoResultadoMock() {
        ClassLoader classLoader = FonteDadosRestTest.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource("mockResultadoFonteExcetoUltimo.html")).getFile());
    }

}
