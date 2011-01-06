package org.jboss.fpak.parser;

import org.jboss.fpak.model.Definition;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mike Brock .
 */
public interface Parser {
    public void parse(InputStream stream, Definition definition) throws IOException;
}
