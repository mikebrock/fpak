package org.jboss.fpak.model.builtin;

import org.jboss.fpak.model.Part;

/**
 * @author Mike Brock .
 */
public class HelpPart implements Part {
    private String helpString;

    public HelpPart(String helpString) {
        this.helpString = helpString;
    }

    public String getHelpString() {
        return helpString;
    }
}
