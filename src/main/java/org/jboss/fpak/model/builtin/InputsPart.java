package org.jboss.fpak.model.builtin;

import org.apache.commons.cli.Options;
import org.jboss.fpak.model.Parm;
import org.jboss.fpak.model.Part;

import java.util.List;

/**
 * @author Mike Brock .
 */
public class InputsPart implements Part {
    private List<Parm> parts;
    private Options options;

    public InputsPart(List<Parm> parts) {
        this.parts = parts;

        this.options = new Options();

        for (Parm parm : parts) {
            options.addOption(parm.getName(), !Boolean.class.isAssignableFrom(parm.getType()), parm.getDescription());
        }
    }

    public List<Parm> getParts() {
        return parts;
    }


    public Options getOptions() {
        return options;
    }
}
