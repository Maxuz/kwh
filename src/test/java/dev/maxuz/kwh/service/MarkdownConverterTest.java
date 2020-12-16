package dev.maxuz.kwh.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownConverterTest {
    private final MarkdownConverter markdownConverter = new MarkdownConverter();

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", null),
                Arguments.of("No markup", "<p>No markup</p>\n"),
                Arguments.of("*With markup*", "<p><em>With markup</em></p>\n")
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void convertTest(String source, String expected) {
        assertEquals(expected, markdownConverter.convertToHtml(source));
    }
}