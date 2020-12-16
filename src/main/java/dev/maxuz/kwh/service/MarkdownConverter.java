package dev.maxuz.kwh.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MarkdownConverter {
    public String convertToHtml(String row) {
        if (StringUtils.isEmpty(row)) {
            return null;
        }
        Parser parser = Parser.builder().build();
        Node document = parser.parse(row);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
