package org.jboss.fpak.tests;

import junit.framework.TestCase;
import org.jboss.fpak.model.Definition;
import org.jboss.fpak.parser.sub.FInputsParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Mike Brock .
 */
public class FInputsParserTests extends TestCase {
    public void testParseInputs() throws IOException {
        FInputsParser parser = new FInputsParser();
        String s = "{String foo : \"This is a foo test\", int amount : \"But this tests the amount\"}";
        parser.parse(new ByteArrayInputStream(s.getBytes()), new Definition());


    }
}