package org.jboss.fpak.parser.sub;

import org.jboss.fpak.model.Definition;
import org.jboss.fpak.model.DefinitionPart;
import org.jboss.fpak.model.builtin.InitPart;
import org.jboss.fpak.parser.Parser;
import org.jboss.fpak.parser.ParserUtil;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * @author Mike Brock .
 */
public class FInitParser implements Parser {
    public void parse(InputStream stream, Definition definition) throws IOException {
        String block = ParserUtil.captureCurlyBlock(stream);
        Serializable compiledExpression = MVEL.compileExpression(block);
        definition.addPart(DefinitionPart.Initializer, new InitPart(compiledExpression));
    }
}
