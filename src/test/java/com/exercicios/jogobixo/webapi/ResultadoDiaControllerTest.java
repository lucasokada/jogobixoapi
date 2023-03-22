package com.exercicios.jogobixo.webapi;

import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.core.usecase.ConsultaResultadoUseCase;
import com.exercicios.jogobixo.core.usecase.ImportarResultadoUseCase;
import com.exercicios.jogobixo.webapi.services.AgendadorExecucoes;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ResultadoDiaController.class)
@Import(AgendadorExecucoes.class)
public class ResultadoDiaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    ImportarResultadoUseCase importarResultado;

    @MockBean
    ConsultaResultadoUseCase consultaResultado;

    private static ResultadoDia mockImportacaoSucesso(LocalDate resultadoEm) {
        var resultado = new ResultadoDia(resultadoEm);
        resultado.inserirResultadoHorario(HorarioJogos.PT, "");
        resultado.inserirResultadoHorario(HorarioJogos.PTM, "");
        return resultado;
    }

    private static Optional<ResultadoDia> mockConsultaDados(LocalDate consultadoEm) {
        ResultadoDia resultado = new ResultadoDia(consultadoEm);
        resultado.inserirResultadoHorario(HorarioJogos.PT, "4222-06");
        resultado.inserirResultadoHorario(HorarioJogos.PTM, "3556-14");
        return Optional.of(resultado);
    }

    private static Optional<ResultadoDia> mockConsultaDadosCompleto(LocalDate consultadoEm) {
        ResultadoDia resultado = new ResultadoDia(consultadoEm);
        resultado.inserirResultadoHorario(HorarioJogos.PT, "4222-06");
        resultado.inserirResultadoHorario(HorarioJogos.PTM, "3556-14");
        resultado.inserirResultadoHorario(HorarioJogos.PTV, "2201-01");
        resultado.inserirResultadoHorario(HorarioJogos.COR, "0947-12");
        resultado.inserirResultadoHorario(HorarioJogos.FED, "7936-09");
        return Optional.of(resultado);
    }

    @Test
    public void deveImportarResultadosComSucesso() throws Exception {
        Mockito.when(importarResultado.importar()).thenReturn(mockImportacaoSucesso(LocalDate.of(2023, 1, 29)));
        mvc.perform(post("/resultado-dia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sorteadoEm").value("2023-01-29"))
                .andExpect(jsonPath("$.horarios").value(Matchers.containsInAnyOrder("PT", "PTM")));
    }

    @Test
    public void naoDeveImportarResultadosQuandoOcorrerErro() throws Exception {
        doThrow(new RuntimeException("Bad Request")).when(importarResultado).importar();

        mvc.perform(post("/resultado-dia"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.descricao").value("Erro de Teste!"));
    }

    @Test
    public void deveConsultarResultadosComSucesso() throws Exception {
        LocalDate resultadoDe = LocalDate.of(2023, 1, 29);
        Mockito.when(consultaResultado.consultarPorData(resultadoDe)).thenReturn(mockConsultaDados(resultadoDe));
        mvc.perform(get("/resultado-dia").param("dataJogo", "2023-01-29"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sorteadoEm").value("2023-01-29"))
                .andExpect(jsonPath("$.horarios[0].horario").value(("PTM")))
                .andExpect(jsonPath("$.horarios[0].numerosSorteados[0]").value("3556-14"))
                .andExpect(jsonPath("$.horarios[1].horario").value(("PT")))
                .andExpect(jsonPath("$.horarios[1].numerosSorteados[0]").value("4222-06"));
    }

    @Test
    public void deveManterOrdemDosHorariosDosResultadosConsultados() throws Exception {
        LocalDate resultadoDe = LocalDate.of(2023, 1, 29);
        Mockito.when(consultaResultado.consultarPorData(resultadoDe)).thenReturn(mockConsultaDadosCompleto(resultadoDe));
        mvc.perform(get("/resultado-dia").param("dataJogo", "2023-01-29"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sorteadoEm").value("2023-01-29"))
                .andExpect(jsonPath("$.horarios[0].horario").value(("PTM")))
                .andExpect(jsonPath("$.horarios[1].horario").value(("PT")))
                .andExpect(jsonPath("$.horarios[2].horario").value(("PTV")))
                .andExpect(jsonPath("$.horarios[3].horario").value(("FED")))
                .andExpect(jsonPath("$.horarios[4].horario").value(("COR")));
    }

    @Test void deveRetornarNullQuandoNaoForEncontradoResultadosNaDataDesejada() throws Exception {
        mvc.perform(get("/resultado-dia").param("dataJogo", "2023-01-29"))
                .andExpect(status().isNotFound());
    }
}
