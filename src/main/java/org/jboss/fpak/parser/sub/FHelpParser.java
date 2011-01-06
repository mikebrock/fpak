package org.jboss.fpak.parser.sub;

import org.jboss.fpak.model.Definition;
import org.jboss.fpak.model.DefinitionPart;
import org.jboss.fpak.model.builtin.HelpPart;
import org.jboss.fpak.parser.Parser;
import org.jboss.fpak.parser.ParserUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mike Brock .
 */
public class FHelpParser implements Parser {
    public void parse(InputStream stream, Definition definition) throws IOException {
        String block = ParserUtil.captureCurlyBlock(stream);
        definition.addPart(DefinitionPart.Help, new HelpPart(block));
    }
}
