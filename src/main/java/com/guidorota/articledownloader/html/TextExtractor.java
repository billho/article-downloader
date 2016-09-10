package com.guidorota.articledownloader.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TextExtractor {

    private TextExtractor() { }

    public static Stream<String> extractText(Elements elements) {
        String text = elements.stream()
                .map(Element::text)
                .collect(Collectors.joining(" "))
                .replaceAll("[\n\r]", "");

        if (text == null || text.isEmpty()) {
            return Stream.empty();
        } else {
            return Stream.of(text);
        }
    }

}
