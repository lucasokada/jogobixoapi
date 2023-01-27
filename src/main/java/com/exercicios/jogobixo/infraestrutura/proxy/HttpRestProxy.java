package com.exercicios.jogobixo.infraestrutura.proxy;

import org.jsoup.nodes.Document;

public interface HttpRestProxy {
    Document getHtmlFromUrl(String url);
}
