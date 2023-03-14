package com.exercicios.jogobixo.infraestrutura.proxy;

import com.exercicios.jogobixo.core.dominio.FonteResultadoRepository;
import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.dto.ConsultaResultadoDto;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class FonteDadoRest implements FonteResultadoRepository {
    @Autowired
    HttpRestProxy httpRestProxy;
    private final LocalDate hoje = LocalDate.now();
    private DateTimeFormatter transformadorAnoMesDia = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String dataAtual = hoje.format(transformadorAnoMesDia);
    private final String url = "https://www.resultadofacil.com.br/resultado-do-jogo-do-bicho/RJ/do-dia/" + dataAtual;
    private final int quantidadeElementosParaUmResultadoPadrao = 4;
    private final int quantidadeSorteiosPorJogo = 10;

    private List<String> removerResultadosRepetidos(List<String> todosResultados) {
        final int quantidadeJogosPadronizados = 5;
        final int quantidadeJogosDespadronizado = 2;
        final int quantidadeElementosParaResultadosDespadronizados = 3;
        final int quantidadeSorteios = 10;

        final int quantidadeElementosIgnorados = (quantidadeJogosPadronizados * quantidadeElementosParaUmResultadoPadrao) +
                (quantidadeJogosDespadronizado * quantidadeElementosParaResultadosDespadronizados);
        final int quantidadeElementosValidos = (quantidadeSorteios * quantidadeElementosParaUmResultadoPadrao);

        List<String> resultadosModificaveis = new ArrayList<>(todosResultados);
        List<String> resultadosSemDuplicatas = new ArrayList<>();

        int quantidadeElementosConsumidos = 0;
        while(quantidadeElementosConsumidos < todosResultados.size()) {
            resultadosModificaveis.subList(0, quantidadeElementosIgnorados).clear();
            quantidadeElementosConsumidos += quantidadeElementosIgnorados;

            List<String> list = new ArrayList<>(resultadosModificaveis.subList(0, quantidadeElementosValidos));
            resultadosModificaveis.subList(0, quantidadeElementosValidos).clear();
            quantidadeElementosConsumidos += quantidadeElementosValidos;

            resultadosSemDuplicatas.addAll(list);
        }

        return resultadosSemDuplicatas;
    }

    private List<String> extrairNumerosSorteadosDoResultado(List<String> resultados) {
        final int posicaoMilhar = 1, posicaoGrupo = 2;

        List<String> valoresSorteados = new ArrayList<>();

        for(int i = 0; i < resultados.size() - 1; i += quantidadeElementosParaUmResultadoPadrao) {
            String milhar = resultados.get(i + posicaoMilhar);
            String grupo = resultados.get(i + posicaoGrupo);
            valoresSorteados.add(milhar + "-" + grupo);
        }

        return valoresSorteados;
    }

    private List<List<String>> extrairResultadosDia(String todosResultados) {
        List<String> resultados = Arrays.stream(todosResultados.replaceAll(" +", " ").split(" ")).toList();
        List<String> resultadosSemDuplicatas = removerResultadosRepetidos(resultados);
        List<String> valoresSorteados = extrairNumerosSorteadosDoResultado(resultadosSemDuplicatas);

        final int quantidadeResultados = valoresSorteados.size();
        final int quantidadeJogos = quantidadeResultados / quantidadeSorteiosPorJogo;
        final int quantidadeResultadosPorJogo = quantidadeResultados / quantidadeJogos;

        List<List<String>> resultadosPorJogo = new ArrayList<>();

        for (int i = 0; i < quantidadeJogos; i++) {
            resultadosPorJogo.add(new ArrayList<>());
            for(int j = 0; j < quantidadeResultadosPorJogo; j++) {
                resultadosPorJogo.get(i).add(valoresSorteados.get((i * quantidadeSorteiosPorJogo) + j));
            }
        }

        return resultadosPorJogo;
    }

    private List<String> recuperaResultadoDoHorario(HorarioJogos horario, String todosResultados) {
        final int ordemHorario = HorarioJogos.recuperaListaHorarios().indexOf(horario);
        List<List<String>> resultadosDia = extrairResultadosDia(todosResultados);

        try {
            return resultadosDia.get(ordemHorario);
        } catch (IndexOutOfBoundsException e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<ConsultaResultadoDto> recuperarPorHorario(Set<HorarioJogos> horarios) {
        Document conteudoPaginaHtml = httpRestProxy.getHtmlFromUrl(url);
        String todosResultados = conteudoPaginaHtml.select("tbody tr td").text();

        List<ConsultaResultadoDto> resultadosRecuperados = new ArrayList<>();
        List<HorarioJogos> resultadosPendentesEm = horarios.stream().toList();

        if(todosResultados.length() > 1) {
            for (HorarioJogos resultadoPendenteEm : resultadosPendentesEm) {
                List<String> resultadosHorario = recuperaResultadoDoHorario(resultadoPendenteEm, todosResultados);
                if (resultadosHorario.size() > 0) {
                    resultadosRecuperados.add(new ConsultaResultadoDto(resultadoPendenteEm, resultadosHorario));
                }
            }
        }

        return resultadosRecuperados;
    }
}
