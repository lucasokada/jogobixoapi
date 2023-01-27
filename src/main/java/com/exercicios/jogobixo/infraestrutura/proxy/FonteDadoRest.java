package com.exercicios.jogobixo.infraestrutura.proxy;

import com.exercicios.jogobixo.core.dominio.FonteResultadoRepository;
import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.dto.ConsultaResultadoDto;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class FonteDadoRest implements FonteResultadoRepository {
    private final String url = "https://www.eojogodobicho.com/deu-no-poste.html";
    Document doc;

    public FonteDadoRest() {
        try {
            this.doc = Jsoup.connect(url).get();
        } catch (IOException exception) {
            this.doc = new Document("");
        }
    }

    public String recuperaPorTagHtml(String tag) {
        Elements conteudo = doc.select(tag);

        return conteudo.text();
    }

    public String recuperaPrimeiroElementoDaTag(String tag) {
        Element conteudo = doc.select(tag).first();
        return conteudo.text();
    }

    @Override
    public List<ConsultaResultadoDto> recuperarPorHorario(Set<HorarioJogos> horarios) {
        return null;
    }
}
