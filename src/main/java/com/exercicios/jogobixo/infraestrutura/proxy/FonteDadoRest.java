package com.exercicios.jogobixo.infraestrutura.proxy;

import com.exercicios.jogobixo.core.dominio.FonteResultadoRepository;
import com.exercicios.jogobixo.core.dominio.HorarioJogos;
import com.exercicios.jogobixo.core.dominio.dto.ConsultaResultadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class FonteDadoRest implements FonteResultadoRepository {
    @Autowired
    HttpRestProxy httpRestProxy;

    private final String url = "https://www.eojogodobicho.com/deu-no-poste.html";

//    public String recuperaPorTagHtml(String tag) {
//        Elements conteudo = doc.select(tag);
//
//        return conteudo.text();
//    }
//
//    public String recuperaPrimeiroElementoDaTag(String tag) {
//        Element conteudo = doc.select(tag).first();
//        return conteudo.text();
//    }

    @Override
    public List<ConsultaResultadoDto> recuperarPorHorario(Set<HorarioJogos> horarios) {

        return List.of();
    }
}
