package dev.maxuz.kwh.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MarkdownConverter {
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    public String convertToHtml(String row) {
        if (StringUtils.isEmpty(row)) {
            return null;
        }
        Node document = parser.parse(row);
        return renderer.render(document);
    }
}
