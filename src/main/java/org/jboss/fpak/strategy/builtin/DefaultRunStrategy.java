package org.jboss.fpak.strategy.builtin;

import org.jboss.fpak.GenerationContext;
import org.jboss.fpak.model.Definition;
import org.jboss.fpak.model.DefinitionPart;
import org.jboss.fpak.model.Part;
import org.jboss.fpak.model.builtin.FilePart;
import org.jboss.fpak.model.builtin.InitPart;
import org.jboss.fpak.strategy.RunStrategy;

import java.util.List;

/**
 * @author Mike Brock .
 */
public class DefaultRunStrategy implements RunStrategy {
    public void doStrategy(GenerationContext context, Definition definition) {
        InitPart initPart = (InitPart) definition.getSinglePart(DefinitionPart.Initializer);

        if (initPart != null) initPart.doInit(context);

        List<Part> files = definition.getPart(DefinitionPart.File);
        for (Part p : files) {
            FilePart fp = (FilePart) p;
            try {
                fp.generate(context);
            } catch (Exception e) {
                throw new RuntimeException("unknown exception", e);
            }
        }
    }
}
