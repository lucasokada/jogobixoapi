package com.exercicios.jogobixo.webapi;

import com.exercicios.jogobixo.core.usecase.ConsultaResultadoUseCase;
import com.exercicios.jogobixo.core.usecase.ImportarResultadoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ResultadoDiaController.class)
public class ResultadoDiaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    ImportarResultadoUseCase importarResultado;

    @MockBean
    ConsultaResultadoUseCase consultaResultado;

    @Test
    public void deveImportarResultadosComSucesso() throws Exception {

        //Mockito.when(importarResultado.importar()) mockar o retorno igual ao esperado abaixo
        mvc.perform(post("/resultado-dia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultadoDe").value("2023-01-29"))
                .andExpect(jsonPath("$.horarios").value(List.of("PT", "PTM")));
    }

    @Test
    public void naoDeveImportarResultadosQuandoOcorrerErro() throws Exception {
        doThrow(new RuntimeException("Bad Request")).when(importarResultado).importar();

        mvc.perform(post("/resultado-dia"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.descricao").value("Erro de Teste!"));
    }
}
