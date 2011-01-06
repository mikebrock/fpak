package org.jboss.fpak.model;

import org.jboss.fpak.model.builtin.MultiPart;

import java.util.*;

/**
 * @author Mike Brock .
 */
public class Definition {
    private Map<String, List<Part>> parts = new HashMap<String, List<Part>>();

    public void addPart(DefinitionPart partEnum, Part part) {
        addPart(partEnum.name(), part);
    }

    public void addPart(String name, Part part) {
        List<Part> lp;
        if (!parts.containsKey(name)) {
            parts.put(name, lp = new ArrayList<Part>());
        } else {
            lp = parts.get(name);
        }

        if (lp.isEmpty() || part instanceof MultiPart) {
            parts.get(name).add(part);
        } else {
            throw new RuntimeException("part already defined: " + name);
        }
    }

    public boolean hasPart(String name) {
        return parts.containsKey(name);
    }

    public Part getSinglePart(DefinitionPart partEnum) {
        return getSinglePart(partEnum.name());
    }

    public Part getSinglePart(String name) {
        List<Part> p = getPart(name);
        if (p == null || p.isEmpty()) return null;
        else if (p.size() != 1) throw new RuntimeException("more than one part fo type: " + name);

        return p.get(0);
    }

    public List<Part> getPart(DefinitionPart partEnum) {
        return getPart(partEnum.name());
    }

    public List<Part> getPart(String name) {
        return parts.get(name);
    }
}
