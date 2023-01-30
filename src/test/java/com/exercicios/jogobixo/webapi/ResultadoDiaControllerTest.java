package com.exercicios.jogobixo.webapi;

import com.exercicios.jogobixo.core.usecase.ConsultaResultadoUseCase;
import com.exercicios.jogobixo.core.usecase.ImportarResultadoUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResultadoDiaController.class)
public class ResultadoDiaControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    ImportarResultadoUseCase importarResultado = Mockito.mock(ImportarResultadoUseCase.class);

    @Mock
    ConsultaResultadoUseCase consultaResultado = Mockito.mock(ConsultaResultadoUseCase.class);

    @InjectMocks
    ResultadoDiaController controller;

    @Test
    public void deveImportarResultadosComSucesso() throws Exception {

        //Mockito.when(importarResultado.importar()) mockar o retorno igual ao esperado abaixo
        mvc.perform(post("resultado-dia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultadoDe").value("2023-01-29"))
                .andExpect(jsonPath("$.horarios").value(List.of("PT", "PTM")));
    }

    @Test
    public void naoDeveImportarResultadosQuandoOcorrerErro() throws Exception {
        Mockito
                .doThrow(new Exception("Erro de Teste!"))
                .when(importarResultado)
                .importar();

        mvc.perform(post("resultado-dia"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.descricao").value("Erro de Teste!"));
    }
}
