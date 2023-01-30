package com.exercicios.jogobixo.webapi;

import com.exercicios.jogobixo.core.usecase.ConsultaResultadoUseCase;
import com.exercicios.jogobixo.core.usecase.ImportarResultadoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/resultado-dia")
public class ResultadoDiaController {
    @Autowired
    ImportarResultadoUseCase importarResultado;

    @Autowired
    ConsultaResultadoUseCase consultaResultado;

}
