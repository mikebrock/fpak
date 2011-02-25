package org.jboss.fpak.tests;

import junit.framework.TestCase;
import org.jboss.fpak.model.Definition;
import org.jboss.fpak.model.DefinitionPart;
import org.jboss.fpak.model.Parm;
import org.jboss.fpak.model.builtin.InputsPart;
import org.jboss.fpak.parser.sub.FInputsParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Mike Brock .
 */
public class FInputsParserTests extends TestCase {
    public void testParseInputs() throws IOException {
        FInputsParser parser = new FInputsParser();
        String s = "{String foo : \"This is a foo test\", int amount : \"But this tests the amount\"}";
        Definition def = new Definition();
        parser.parse(new ByteArrayInputStream(s.getBytes()), def);

        System.out.println();

        InputsPart part = (InputsPart) def.getSinglePart(DefinitionPart.Inputs);

        Iterator<Parm> parmIterator = part.getParts().iterator();

        Parm parm = parmIterator.next();
        assertEquals("foo", parm.getName());
        assertEquals(String.class, parm.getType());
        assertEquals("This is a foo test", parm.getDescription());

        parm = parmIterator.next();
        assertEquals("amount", parm.getName());
        assertEquals(Integer.class, parm.getType());
        assertEquals("But this tests the amount", parm.getDescription());
    }

    public void testParseInputs2() throws IOException {


    }
}