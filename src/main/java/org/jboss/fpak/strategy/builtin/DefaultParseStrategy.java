package org.jboss.fpak.strategy.builtin;

import org.jboss.fpak.GenerationContext;
import org.jboss.fpak.model.Definition;
import org.jboss.fpak.parser.FPakParser;
import org.jboss.fpak.parser.sub.FHelpParser;
import org.jboss.fpak.parser.sub.FInitParser;
import org.jboss.fpak.parser.sub.FInputsParser;
import org.jboss.fpak.parser.sub.FileTemplateParser;
import org.jboss.fpak.strategy.ParseStrategy;

import java.io.*;

/**
 * @author Mike Brock .
 */
public class DefaultParseStrategy implements ParseStrategy {
    public Definition doStrategy(GenerationContext ctx) {
        ctx.setParser("init", new FInitParser());
        ctx.setParser("help", new FHelpParser());
        ctx.setParser("inputs", new FInputsParser());

        ctx.setParser(FPakParser.FILE_TEMPLATE_PARSER, new FileTemplateParser());

        FPakParser fPakParser = new FPakParser(ctx.getParsers());
        Definition definition = new Definition();

        for (InputStream stream: ctx.getInputStreams()) {
            try {
                fPakParser.parse(stream, definition);

            } catch (IOException e) {
                throw new RuntimeException("unknown exception: " + e.getMessage(), e);
            } finally {
                try {
                    if (stream != null) stream.close();
                } catch (Exception e) {
                    throw new RuntimeException("failed to close stream", e);
                }
            }
        }

        for (File template : ctx.getTemplates()) {
            InputStream stream = null;
            try {
                stream = new BufferedInputStream(new FileInputStream(template));

                fPakParser.parse(stream, definition);

            } catch (IOException e) {
                throw new RuntimeException("unknown exception: " + e.getMessage(), e);
            } finally {
                try {
                    if (stream != null) stream.close();
                } catch (Exception e) {
                    throw new RuntimeException("failed to close stream", e);
                }
            }
        }

        return definition;
    }
}
