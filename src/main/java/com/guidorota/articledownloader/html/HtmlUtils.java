package com.guidorota.articledownloader.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.stream.Collectors;

public final class HtmlUtils {

    private HtmlUtils() { }

    public static String extractText(Element element) {
        return element.text()
                .replaceAll("[\n\r]", "");
    }

    public static String extractText(Elements elements) {
        return elements.stream()
                .map(Element::text)
                .collect(Collectors.joining(" "))
                .replaceAll("[\n\r]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

}
