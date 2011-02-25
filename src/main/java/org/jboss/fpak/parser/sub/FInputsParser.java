package org.jboss.fpak.parser.sub;

import org.jboss.fpak.model.Definition;
import org.jboss.fpak.model.DefinitionPart;
import org.jboss.fpak.model.Parm;
import org.jboss.fpak.model.builtin.InputsPart;
import org.jboss.fpak.parser.Parser;
import org.jboss.fpak.parser.ParserUtil;
import org.mvel2.MVEL;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mike Brock .
 */
public class FInputsParser implements Parser {
    public void parse(InputStream stream, Definition definition) throws IOException {
        String block = ParserUtil.captureCurlyBlock(stream).trim();

        List<Parm> parms = new ArrayList<Parm>();
        List<String> els = new ArrayList<String>();

        int start = 0;
        String tk;

        for (int i = 0; i <= block.length(); i++) {
            switch (i < block.length() ? block.charAt(i) : ',') {
                case ':':
                    els.add(block.substring(start, i).trim());
                    start = i + 1;

                    if (els.size() != 2) {
                        throw new RuntimeException("bad syntax: " + block);
                    }

                    break;
                case '"':
                case '\'':
                    i = ParserUtil.balancedCapture(block, i, block.charAt(i));
                    break;

                case ',':
                    els.add(block.substring(start, i).trim());

                    Iterator<String> iter = els.iterator();
                    parms.add(new Parm(MVEL.eval(iter.next(), Class.class), iter.next(), MVEL.eval(iter.next(), String.class)));

                    start = ++i;
                    while (i < block.length() && Character.isWhitespace(block.charAt(i))) i++;

                    els.clear();
                    break;
                default:
                    if (els.isEmpty() && Character.isWhitespace(block.charAt(i))) {
                        els.add(block.substring(start, start = i).trim());
                        while (i < block.length() && Character.isWhitespace(block.charAt(i))) i++;
                    }

            }
        }

        definition.addPart(DefinitionPart.Inputs, new InputsPart(parms));
    }

}
