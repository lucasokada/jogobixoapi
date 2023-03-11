package com.exercicios.jogobixo.infraestrutura.proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class HttpRestProxyImp implements HttpRestProxy {
    @Override
    public Document getHtmlFromUrl(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
