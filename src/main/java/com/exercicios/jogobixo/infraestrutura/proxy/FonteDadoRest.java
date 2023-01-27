package com.exercicios.jogobixo.infraestrutura.proxy;

import com.exercicios.jogobixo.core.dominio.FonteResultadoRepository;
import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.dto.ConsultaResultadoDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class FonteDadoRest implements FonteResultadoRepository {
    @Autowired
    HttpRestProxy httpRestProxy;

    private final String url = "https://www.eojogodobicho.com/deu-no-poste.html";


    private LocalDate extrairData(String conteudo) {
        String prefixo = conteudo.substring(0, conteudo.indexOf(",") + 1);
        String sufixo = conteudo.substring(conteudo.indexOf("."), conteudo.length());
        String[] dataJogo = conteudo.replace(prefixo, "").replace(sufixo, "").replace("de", "").trim().split("\\s+");
        String diaJogo = dataJogo[0];
        String mesJogo = dataJogo[1];
        String anoJogo = dataJogo[2];

        return LocalDate.of(Integer.parseInt(anoJogo), Meses.converterParaMonth(Meses.valueOf(mesJogo.toUpperCase())),
                Integer.parseInt(diaJogo));
    }

    private List<List<String>> extrairResultadosDia(String todosResultados) {
        final int quantidadeJogosDiarios = 7;
        final int quantidadeSorteios = 5;
        final int tamanhoListaResultados = (quantidadeSorteios + 1) * quantidadeJogosDiarios;

        List<String>resultados = new ArrayList<>(Arrays.stream(todosResultados.split(" ", tamanhoListaResultados + 1)).toList());
        resultados.remove(resultados.size() - 1);

        List<List<String>> resultadosPorSorteio = new ArrayList<>();

        int controladorResultados = 0;
        for (int i = 0; i < quantidadeJogosDiarios; i++) {
            controladorResultados++;
            resultadosPorSorteio.add(new ArrayList<>());
            for(int j = 0; j < quantidadeSorteios; j++) {
                resultadosPorSorteio.get(i).add(resultados.get(controladorResultados));
                controladorResultados++;
            }
        }

        return resultadosPorSorteio;
    }

    private List<String> recuperaResultadoDoHorario(HorarioJogos horario, String todosResultados) {
        final int ordemHorario = HorarioJogos.recuperaListaHorarios().indexOf(horario);
        List<List<String>> resultadosDia = extrairResultadosDia(todosResultados);
        List<String> resultadosBuscados = new ArrayList<>();

        var resultado = "";
        int i = 0;
        while(i < resultadosDia.size() && !resultado.matches("000(.*)-25")) {
            resultado = resultadosDia.get(i).get(ordemHorario);
            if (!resultado.matches("000(.*)-25")) {
                resultadosBuscados.add(resultado);
            }

            i++;
        }

        return resultadosBuscados;
    }

    @Override
    public List<ConsultaResultadoDto> recuperarPorHorario(Set<HorarioJogos> horarios) {
        Document documentoHtml = httpRestProxy.getHtmlFromUrl(url);
        String conteudoDataJogo = documentoHtml.select("p").first().text();
        LocalDate dataJogo = extrairData(conteudoDataJogo);
        String todosResultados = documentoHtml.select("tbody tr td").text();

        List<ConsultaResultadoDto> resultadosRecuperados = new ArrayList<>();
        List<HorarioJogos> horariosBuscados = horarios.stream().toList();

        for(int i = 0; i < horariosBuscados.size(); i++) {
            List<String> resultadosHorario = recuperaResultadoDoHorario(horariosBuscados.get(i), todosResultados);
            if(resultadosHorario.size() > 0) {
                resultadosRecuperados.add(new ConsultaResultadoDto(horariosBuscados.get(i), resultadosHorario));
            }
        }

        return resultadosRecuperados;
    }
}
