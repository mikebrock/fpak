package org.jboss.fpak.parser;

import org.jboss.fpak.model.Definition;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Mike Brock .
 */
public class FPakParser implements Parser {
    public static final String FILE_TEMPLATE_PARSER = "FileTemplateParser";

    private Map<String, Parser> parsers;

    public FPakParser(Map<String, Parser> parsers) {
        this.parsers = parsers;
    }

    public void parse(InputStream stream, Definition definition) throws IOException {
        int read;

        while ((read = stream.read()) != -1) {
            switch (read) {
                case '@':
                    String nodeType = ParserUtil.captureTo(stream, ':');
                    if (!parsers.containsKey(nodeType)) {
                        throw new RuntimeException("unknown node type: " + nodeType);
                    }

                    parsers.get(nodeType).parse(stream, definition);
                    break;

                case '+':
                    if ((read = stream.read()) == '+') {
                        if (!parsers.containsKey(FILE_TEMPLATE_PARSER)) {
                            throw new RuntimeException("default file template parser is undefined: " + FILE_TEMPLATE_PARSER);
                        }

                        parsers.get(FILE_TEMPLATE_PARSER).parse(stream, definition);
                    }
                    break;
            }
        }
    }
}
