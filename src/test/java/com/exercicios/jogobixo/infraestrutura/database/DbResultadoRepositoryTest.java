package com.exercicios.jogobixo.infraestrutura.database;

import com.exercicios.jogobixo.core.dominio.ResultadoDia;
import com.exercicios.jogobixo.mock.ResultadoDiaMock;
import jakarta.annotation.security.RunAs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class DbResultadoRepositoryTest {
    @Autowired
    DbResultadoRepository DbResultadoRepository;

    @Test
    @Transactional
    public void deveSalvarUmResultadoEConsultarPorData() {
        LocalDate dataAtual = LocalDate.now();
        ResultadoDia resultadoMock = ResultadoDiaMock.mockResultadoDiaCompleto(dataAtual);

        DbResultadoRepository.salvar(resultadoMock);
        Optional<ResultadoDia> resultadoPersistido = DbResultadoRepository.consultarPorData(dataAtual);

        assertTrue(resultadoPersistido.isPresent());

        ResultadoDia resultadoValor = resultadoPersistido.get();
        assertEquals(resultadoValor.getSorteadoEm(), resultadoMock.getSorteadoEm());
        assertEquals(resultadoValor.getHorarios(), resultadoMock.getHorarios());
    }

    @Test
    public void deveConsultarUmResultadoInexistente() {
        LocalDate dataFiltro = LocalDate.now().plusDays(1);
        Optional<ResultadoDia> resultadoPersistido = DbResultadoRepository.consultarPorData(dataFiltro);

        assertTrue(resultadoPersistido.isEmpty());
    }
}
