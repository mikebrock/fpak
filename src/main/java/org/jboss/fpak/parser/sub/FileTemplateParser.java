package org.jboss.fpak.parser.sub;

import org.jboss.fpak.model.Definition;
import org.jboss.fpak.model.DefinitionPart;
import org.jboss.fpak.model.builtin.FilePart;
import org.jboss.fpak.parser.Parser;
import org.jboss.fpak.parser.ParserUtil;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mike Brock .
 */
public class FileTemplateParser implements Parser {
    public void parse(InputStream stream, Definition definition) throws IOException {
        String fileString = ParserUtil.captureTo(stream, ':');

        FilePart filePart = new FilePart();
        filePart.setPathTemplate(fileString);

        String block = ParserUtil.captureCurlyBlock(stream);

        filePart.setTemplate(block);

        definition.addPart(DefinitionPart.File, filePart);
    }
}
